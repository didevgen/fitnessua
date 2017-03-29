package ua.malibu.ostpc.controllers.employeePreference;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.controllers.BaseController;
import ua.malibu.ostpc.daos.EmployeePreferenceDAO;
import ua.malibu.ostpc.dtos.employeePreferenceDTO.EmployeePreferenceDTO;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.*;

public class EmployeePreferenceController extends BaseController{
    @Autowired
    private EmployeePreferenceDAO employeePreferenceDAO;

    @RequestMapping(value = "/employeepreference/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EmployeePreferenceDTO> getEmployeePreference (@PathVariable(name = "uuid") String uuid) {
        EmployeePreference employeePreference = employeePreferenceDAO.get(uuid);
        if (employeePreference != null){
            return new ResponseEntity<>(new EmployeePreferenceDTO()
                    .convert(employeePreference), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/employeepreference/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteEmployeePreference (@PathVariable(name = "uuid") String uuid) {
        EmployeePreference employeePreference = employeePreferenceDAO.get(uuid);
        if (employeePreference != null){
            employeePreferenceDAO.delete(employeePreference);
            return new ResponseEntity<Boolean>(HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/employeepreference/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<EmployeePreferenceDTO> updateEmployeePreference (@PathVariable(name = "uuid") String uuid,
                                                                   @RequestBody EmployeePreferenceDTO employeePreferenceDTO) {
        EmployeePreference employeePreference = employeePreferenceDAO.get(uuid);
        if (employeePreference != null){
            employeePreference.setUser(new JPAQuery<User>(entityManager)
                    .from(QUser.user)
                    .where(QUser.user.uuid.eq(uuid)).fetchOne());
            employeePreference.setClubs(new JPAQuery<Club>(entityManager)
                    .from(QClub.club)
                    .where(QClub.club.uuid.in(employeePreferenceDTO.getClubs())).fetch());
            employeePreferenceDAO.update(employeePreference);
            return new ResponseEntity<EmployeePreferenceDTO>(new EmployeePreferenceDTO()
                    .convert(employeePreference), HttpStatus.OK);
        }
        else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/employeepreference/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<EmployeePreferenceDTO> createEmployeePreference (@RequestBody EmployeePreferenceDTO employeePreferenceDTO){
        EmployeePreference employeePreference = new EmployeePreference();
        employeePreference.setUser(new JPAQuery<User>(entityManager)
                .from(QUser.user)
                .where(QUser.user.uuid.eq(employeePreferenceDTO.getUuid())).fetchOne());
        employeePreference.setClubs(new JPAQuery<Club>(entityManager)
                .from(QClub.club)
                .where(QClub.club.uuid.in(employeePreferenceDTO.getClubs())).fetch());
        employeePreferenceDAO.insert(employeePreference);
        return new ResponseEntity<EmployeePreferenceDTO>(new EmployeePreferenceDTO().convert(employeePreference), HttpStatus.OK);
    }
}
