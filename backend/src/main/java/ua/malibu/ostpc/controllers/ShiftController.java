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
        /**
         * You already have ShiftDAO. Please autowire (see @Autowired) ShiftDAO to the controller
         */
         Shift shift = new JPAQuery<Shift>(entityManager)
                 .from(QShift.shift)
                 .where(QShift.shift.uuid.eq(uuid))
                 .fetchOne();
        /**
         * Don't create a new variable
         * you can do this by new ShiftDTO().convert(shift)
         */
         ShiftDTO shiftDTO = new ShiftDTO();
        return new ResponseEntity<ShiftDTO>(shiftDTO.convert(shift), HttpStatus.OK);
    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteShift(@PathVariable (name = "uuid", required = true) String uuid)
    {
        /**
         * Please, read about @Transactional interface
         * I suppose, that JPADeleteClause will not do rollback
         * Maybe it would be better to find entity and then remove it
         * by using entityManager (see EntityManager API docs)
         */
        new JPADeleteClause(entityManager, QShift.shift)
                .where (QShift.shift.uuid.eq(uuid))
                .execute();
        /**
         * Why do you return true?
         * What will you do, when the specified shift is not found
         * Or you have faced the delete constraint???
         */
    return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    //Name as updateShift
    //Request body must be DTO instance not this
    // Like @RequestBody ShiftDTO shift (or some another class)
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
            //you can use JPA#em.merge instead
            new JPAUpdateClause(entityManager, QShift.shift)
                    .where(QShift.shift.uuid.eq(uuid))
                    .set(QShift.shift, shift)
                    .execute();
            ShiftDTO shiftDTO = new ShiftDTO();
            return new ResponseEntity<ShiftDTO>(shiftDTO.convert(shift), HttpStatus.OK);
        }
        else {
            //see testController and do throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    //createShift
    //the same about RequestBody
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
            //use JPA#em.persist
            //And when you are making a new instance it must not be Update statement :)
            new JPAUpdateClause(entityManager, QShift.shift)
                    .set(QShift.shift, shift)
                    .execute();
            ShiftDTO shiftDTO = new ShiftDTO();
            //use just 200 - OK
            return new ResponseEntity<ShiftDTO>(shiftDTO.convert(shift), HttpStatus.CREATED);
        }
        else {
            //throw new
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
