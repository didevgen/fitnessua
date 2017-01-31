package ua.malibu.ostpc.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.malibu.ostpc.dtos.auth._Login;
import ua.malibu.ostpc.services.UserService;
import ua.malibu.ostpc.utils.auth.TokenGenerator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@RestController
@Transactional
public class LoginController {
    protected static final transient Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    UserService userService;
    @PersistenceContext
    protected EntityManager entityManager;

    @RequestMapping(value="/login", method=RequestMethod.POST, consumes = "application/json")
    public ResponseEntity login (@RequestBody _Login login) throws Exception {
        String token = tokenGenerator.issueToken(UUID.randomUUID().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}