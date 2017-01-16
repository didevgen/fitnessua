package ua.malibu.ostpc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.dtos.auth._Login;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.users.User;
import ua.malibu.ostpc.utils.auth.Verifier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@RestController
@Transactional
public class LoginController {
    protected static final transient Logger logger = Logger.getLogger(LoginController.class.getName());

    @PersistenceContext
    protected EntityManager entityManager;

   /* @RequestMapping("/")
    public ResponseEntity login (@RequestBody _Login login, HttpServletResponse resp) throws RestException {
        User user = Verifier.VerifyCreds(login, entityManager);
    } */

}
