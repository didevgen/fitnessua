package ua.malibu.ostpc.controllers;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import javafx.beans.binding.BooleanExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.dtos.shift.ShiftDTO;
import ua.malibu.ostpc.models.QShift;
import ua.malibu.ostpc.models.Shift;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.models.WorkDay;

import java.util.List;

/**
 * Created by Игорь on 31.01.2017.
 */
@RestController
public class ShiftController extends BaseController
{

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ShiftDTO> getShift(@PathVariable (name = "uuid", required = true) String uuid)
    {
         Shift shift = new JPAQuery<Shift>(entityManager)
                 .from(QShift.shift)
                 .where(QShift.shift.uuid.eq(uuid))
                 .fetchOne();
         ShiftDTO shiftDTO = new ShiftDTO();
        return new ResponseEntity<ShiftDTO>(shiftDTO.convert(shift), HttpStatus.OK);
    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteShift(@PathVariable (name = "uuid", required = true) String uuid)
    {
        new JPADeleteClause(entityManager, QShift.shift)
                .where (QShift.shift.uuid.eq(uuid))
                .execute();
    return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ShiftDTO> putShift (@PathVariable (name = "uuid") String uuid,
                                       @RequestBody Long id,
                                       WorkDay workingDay,
                                       Integer shiftOrdinal,
                                       List<User> workersOnShift)
    {
        Shift shift = new JPAQuery<Shift>(entityManager)
                .from(QShift.shift)
                .where(QShift.shift.uuid.eq(uuid))
                .fetchOne();
        if(shift != null) {
            shift.setShiftOrdinal(shiftOrdinal);
            shift.setWorkersOnShift(workersOnShift);
            shift.setWorkingDay(workingDay);
            shift.setId(id);
            new JPAUpdateClause(entityManager, QShift.shift)
                    .where(QShift.shift.uuid.eq(uuid))
                    .set(QShift.shift, shift)
                    .execute();
            ShiftDTO shiftDTO = new ShiftDTO();
            return new ResponseEntity<ShiftDTO>(shiftDTO.convert(shift), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ShiftDTO> postShift (@PathVariable (name = "uuid") String uuid,
                                        @RequestBody Long id,
                                        WorkDay workingDay,
                                        Integer shiftOrdinal,
                                        List<User> workersOnShift)
    {
        Shift isShift = new JPAQuery<Shift>(entityManager)
                .from(QShift.shift)
                .where(QShift.shift.uuid.eq(uuid))
                .fetchOne();
        if(isShift == null){
            Shift shift = new Shift();
            shift.setUuid(uuid);
            shift.setShiftOrdinal(shiftOrdinal);
            shift.setWorkersOnShift(workersOnShift);
            shift.setWorkingDay(workingDay);
            shift.setId(id);
            new JPAUpdateClause(entityManager, QShift.shift)
                    .set(QShift.shift, shift)
                    .execute();
            ShiftDTO shiftDTO = new ShiftDTO();
            return new ResponseEntity<ShiftDTO>(shiftDTO.convert(shift), HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
