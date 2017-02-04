package ua.malibu.ostpc.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.daos.UserDAO;
import ua.malibu.ostpc.dtos.users.FullUserDTO;
import ua.malibu.ostpc.dtos.users.SimpleUserDTO;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.User;

@RestController
public class UserController extends BaseController {

    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SimpleUserDTO> readUser(@PathVariable (name = "uuid", required = true) String uuid) {
        return new ResponseEntity<>(new SimpleUserDTO().convert(new UserDAO().get(uuid)), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteUser(@PathVariable (name = "uuid", required = true) String uuid) {
        User user = new UserDAO().getEntityManager().find(User.class,uuid);
        if (user != null) {
            new UserDAO().delete(user);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<FullUserDTO> updateUser(@PathVariable (name = "uuid", required = true) String uuid,
                                                    @RequestBody FullUserDTO fullUser)
    {
        User user = new UserDAO().get(uuid);
        if(user != null) {
            user.setName(fullUser.getName());
            user.setSurname(fullUser.getSurname());
            user.setMiddleName(fullUser.getMiddleName());
            user.setBirthday(fullUser.getBirthday());
            user.setAddress(fullUser.getAddress());
            user.setEmail(fullUser.getEmail());
            user.setPhoneNumber(fullUser.getPhoneNumber());
            user.setRole(fullUser.getRole());
            user.setShifts(fullUser.getShifts());
            user.setPassword(fullUser.getPassword());

            new UserDAO().update(user);

            return new ResponseEntity<FullUserDTO>(new FullUserDTO().convert(user), HttpStatus.OK);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FullUserDTO> createUser(@PathVariable (name = "uuid", required = true) String uuid,
                                                  @RequestBody FullUserDTO fullUser)
    {
        User user = new UserDAO().get(uuid);
        if(user == null) {
            user.setName(fullUser.getName());
            user.setSurname(fullUser.getSurname());
            user.setMiddleName(fullUser.getMiddleName());
            user.setBirthday(fullUser.getBirthday());
            user.setAddress(fullUser.getAddress());
            user.setEmail(fullUser.getEmail());
            user.setPhoneNumber(fullUser.getPhoneNumber());
            user.setRole(fullUser.getRole());
            user.setShifts(fullUser.getShifts());
            user.setPassword(fullUser.getPassword());

            new UserDAO().insert(user);

            return new ResponseEntity<FullUserDTO>(new FullUserDTO().convert(user), HttpStatus.OK);
        } else {
            throw new RestException(HttpStatus.CONFLICT, 409001, "Entity does already exist");
        }
    }

}
