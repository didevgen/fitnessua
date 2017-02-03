package ua.malibu.ostpc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.daos.ShiftDAO;
import ua.malibu.ostpc.dtos.shift.ShiftDTO;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.Shift;

/**
 * Created by Игорь on 31.01.2017.
 */
@RestController
public class ShiftController extends BaseController
{
    @Autowired
    private ShiftDAO shiftDAO;

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ShiftDTO> getShift(@PathVariable (name = "uuid", required = true) String uuid)
    {
        /**
         * You already have ShiftDAO. Please autowire (see @Autowired) ShiftDAO to the controller
         */

        /**
         * Don't create a new variable
         * you can do this by new ShiftDTO().convert(shift)
         */
        if(shiftDAO.get(uuid) != null){
            return new ResponseEntity<>(new ShiftDTO().convert(shiftDAO.get(uuid)), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
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
        if(shiftDAO.get(uuid) != null){
            shiftDAO.delete(shiftDAO.get(uuid));
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
        /**
         * Why do you return true?
         * What will you do, when the specified shift is not found
         * Or you have faced the delete constraint???
         */

    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    //Name as updateShift
    //Request body must be DTO instance not this
    // Like @RequestBody ShiftDTO shift (or some another class)
    public ResponseEntity<ShiftDTO> updateShift (@PathVariable (name = "uuid") String uuid,
                                       @RequestBody ShiftDTO shiftDTO)
    {
        if(shiftDAO.get(uuid) != null) {
            shiftDAO.get(uuid).setShiftOrdinal(shiftDTO.getShiftOrdinal());
            shiftDAO.get(uuid).setWorkersOnShift(shiftDTO.getWorkersOnShift());
            shiftDAO.get(uuid).setWorkingDay(shiftDTO.getWorkingDay());
            //you can use JPA#em.merge instead
            shiftDAO.update(shiftDAO.get(uuid));
            return new ResponseEntity<>(new ShiftDTO().convert(shiftDAO.get(uuid)), HttpStatus.OK);
        }
        else {
            //see testController and do throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }

    }

    @RequestMapping(value = "/shift/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    //createShift
    //the same about RequestBody
    public ResponseEntity<ShiftDTO> createShift (@PathVariable (name = "uuid") String uuid,
                                        @RequestBody ShiftDTO shiftDTO)
    {
        if(shiftDAO.get(uuid) == null){
            Shift shift = new Shift();
            shift.setUuid(uuid);
            shift.setShiftOrdinal(shiftDTO.getShiftOrdinal());
            shift.setWorkersOnShift(shiftDTO.getWorkersOnShift());
            shift.setWorkingDay(shiftDTO.getWorkingDay());
            //use JPA#em.persist
            //And when you are making a new instance it must not be Update statement :)
            shiftDAO.insert(shift);
            //use just 200 - OK
            return new ResponseEntity<>(shiftDTO.convert(shift), HttpStatus.OK);
        }
        else {
            //throw new
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }
}
