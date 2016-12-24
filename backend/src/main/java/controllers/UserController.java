package controllers;

import dtos.auth._User;
import models.auth.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Transactional
    @RequestMapping("/baseurl")
    public User mainMethod(@RequestBody _User user) {
        User u = new User();
        u.setLogin(user.getName());
        u.setId(1L);
        return u;
    }

}
