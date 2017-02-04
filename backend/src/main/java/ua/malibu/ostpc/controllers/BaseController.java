package ua.malibu.ostpc.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.services.UserService;
import ua.malibu.ostpc.utils.MD5;

import java.security.NoSuchAlgorithmException;

@Transactional
@Controller
public class BaseController {

    protected static final transient Logger logger = Logger.getLogger(BaseController.class.getName());

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private UserService userService;


    public BaseController() {
    }

    @RequestMapping("/blah")
    public ResponseEntity someMethod(HttpServletRequest r) throws NoSuchAlgorithmException{
        for (String re : r.getParameterMap().keySet()) {
            System.out.println(re);
        }
        User user = new User();
        user.setSurname("petrov");
        user.setName("petr");
        user.setBirthday(new DateTime(1,2,3,4,5));
        user.setEmail("qwe");
        user.setPassword(MD5.encrypt("rty"));
        userService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
