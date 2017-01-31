package ua.malibu.ostpc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.dtos.auth._Login;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.services.UserService;
import ua.malibu.ostpc.utils.MD5;
import ua.malibu.ostpc.utils.auth.Fetcher;
import ua.malibu.ostpc.utils.auth.TokenGenerator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

@RestController
@Transactional
public class LoginController {
    protected static final transient Logger logger = Logger.getLogger(LoginController.class.getName());

    private Fetcher fetcher = new Fetcher();
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    UserService userService;
    @PersistenceContext
    protected EntityManager entityManager;

    @RequestMapping(value="/login", method=RequestMethod.POST, consumes = "application/json")
    public ResponseEntity login (@RequestBody _Login login) throws Exception {
        User user1 = fetcher.FetchUser(login, entityManager);
        String token = tokenGenerator.issueToken(user1.getUuid());
        HttpHeaders headers = new HttpHeaders();
        System.out.println(token);
        headers.set("token", token);
        return new ResponseEntity<>(user1, headers, HttpStatus.OK);
    }

}
