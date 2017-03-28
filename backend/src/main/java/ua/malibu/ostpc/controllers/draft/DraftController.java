package ua.malibu.ostpc.controllers.draft;

import com.querydsl.jpa.impl.JPAQuery;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.controllers.BaseController;
import ua.malibu.ostpc.daos.DraftDAO;
import ua.malibu.ostpc.dtos.draft.DraftDTO;
import ua.malibu.ostpc.enums.ScheduleStatus;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.Draft;
import ua.malibu.ostpc.models.QWorkDay;
import ua.malibu.ostpc.models.WorkDay;

import java.util.ArrayList;

public class DraftController extends BaseController {
    @Autowired
    private DraftDAO draftDAO;

    @RequestMapping(value = "/draft/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DraftDTO> getDraft (@PathVariable(name = "uuid") String uuid){
        Draft draft = draftDAO.get(uuid);
        if (draft != null){
            return new ResponseEntity<>(new DraftDTO().convert(draft), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/draft/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteDraft (@PathVariable (name = "uuid") String uuid){
        Draft draft = draftDAO.get(uuid);
        if (draft != null){
            draftDAO.delete(draft);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/draft/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DraftDTO> updateDraft (@PathVariable (name = "uuid") String uuid,
                                                       @RequestBody DraftDTO draftDTO) {
        Draft draft = draftDAO.get(uuid);
        if (draft != null){
            draft.setStartDate(DateTime.parse(draftDTO.getStartDate()));
            draft.setEndDate(DateTime.parse(draftDTO.getEndDate()));
            draft.setStatus(draftDTO.getStatus());
            draft.setWorkingDays(new ArrayList<>(new JPAQuery<WorkDay>(entityManager)
                    .from(QWorkDay.workDay)
                    .where(QWorkDay.workDay.uuid.in(draftDTO.getWorkingDays())).fetch()));
            draftDAO.update(draft);
            return new ResponseEntity<>(new DraftDTO().convert(draft), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/draft/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DraftDTO> createDraft (@RequestBody DraftDTO draftDTO){
        Draft draft = new Draft();
        draft.setStartDate(DateTime.parse(draftDTO.getStartDate()));
        draft.setEndDate(DateTime.parse(draftDTO.getEndDate()));
        draft.setStatus(ScheduleStatus.SUPPOSED);
        draftDAO.insert(draft);
        return new ResponseEntity<>(new DraftDTO().convert(draft), HttpStatus.OK);
    }
}
