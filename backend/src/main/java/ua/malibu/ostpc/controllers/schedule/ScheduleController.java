package ua.malibu.ostpc.controllers.schedule;

import com.querydsl.jpa.impl.JPAQuery;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.controllers.BaseController;
import ua.malibu.ostpc.daos.ScheduleDAO;
import ua.malibu.ostpc.dtos.schedule.ScheduleDTO;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.*;
import ua.malibu.ostpc.utils.ReportGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ScheduleController extends BaseController{
    @Autowired
    private ScheduleDAO scheduleDAO;

    @RequestMapping(value = "/schedule/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ScheduleDTO> getSchedule (@PathVariable (name = "uuid") String uuid){
        Schedule schedule = scheduleDAO.get(uuid);
        if (schedule != null){
            return new ResponseEntity<>(new ScheduleDTO().convert(schedule), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(name="/schedule/api/report/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity getReport( String uuid, HttpServletResponse resp) {
        Schedule schedule = scheduleDAO.get("1");
        if (schedule != null){
            ReportGenerator generator = new ReportGenerator(schedule);
            try {
                StreamUtils.copy(generator.generateReport(), resp.getOutputStream());
                resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            } catch (IOException e) {
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, 50000,
                        "IOException during output stream generation in ScheduleController");
            }
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/schedule/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteSchedule (@PathVariable (name = "uuid") String uuid){
        Schedule schedule = scheduleDAO.get(uuid);
        if (schedule != null){
            scheduleDAO.delete(schedule);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/schedule/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ScheduleDTO> updateSchedule (@PathVariable (name = "uuid") String uuid,
                                                       @RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDAO.get(uuid);
        if (schedule != null){
            schedule.setStartDate(DateTime.parse(scheduleDTO.getStartDate()));
            schedule.setEndDate(DateTime.parse(scheduleDTO.getEndDate()));
            schedule.setStatus(scheduleDTO.getStatus());
            schedule.setWorkingDays(new ArrayList<>(new JPAQuery<WorkDay>(entityManager)
                    .from(QWorkDay.workDay)
                    .where(QWorkDay.workDay.uuid.in(scheduleDTO.getWorkingDays())).fetch()));
            scheduleDAO.update(schedule);
            return new ResponseEntity<>(new ScheduleDTO().convert(schedule), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/schedule/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ScheduleDTO> createSchedule (@RequestBody ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setStartDate(DateTime.parse(scheduleDTO.getStartDate()));
        schedule.setEndDate(DateTime.parse(scheduleDTO.getEndDate()));
        schedule.setStatus(scheduleDTO.getStatus());
        schedule.setWorkingDays(new ArrayList<>(new JPAQuery<WorkDay>(entityManager)
                .from(QWorkDay.workDay)
                .where(QWorkDay.workDay.uuid.in(scheduleDTO.getWorkingDays())).fetch()));
        scheduleDAO.insert(schedule);
        return new ResponseEntity<>(new ScheduleDTO().convert(schedule), HttpStatus.OK);
    }
}
