package ua.malibu.ostpc.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.services.UserService;
import ua.malibu.ostpc.services.redis.RedisRepository;
import ua.malibu.ostpc.utils.MD5;
import ua.malibu.ostpc.utils.auth.TokenGenerator;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Transactional
public class LoginController {
    protected static final transient Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/login", method=RequestMethod.POST, consumes = "application/json")
    public ResponseEntity login (HttpServletRequest req) throws Exception {
        String email = (String)req.getAttribute("login");
        User user = userService.getUser(email);
        if (user == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 40001, "User with email " +
                    email + " was not found!");
        } else if (!MD5.encrypt((String)req.getAttribute("password")).equals(user.getPassword())) {
            throw new RestException(HttpStatus.UNAUTHORIZED, 40003, "Bad credentials!");
        }
        String token = tokenGenerator.issueToken(user.getUuid());
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        System.out.println(token);
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

}