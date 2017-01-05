package ua.malibu.ostpc.controllers;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.malibu.ostpc.dtos.auth.Wrapper;
import ua.malibu.ostpc.dtos.auth._Login;
import ua.malibu.ostpc.dtos.auth._User;
import ua.malibu.ostpc.models.auth.QUser;
import ua.malibu.ostpc.models.auth.User;
import ua.malibu.ostpc.utils.MD5;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

@RestController
@Transactional
public class LoginController {

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("/api/login")
    public ResponseEntity login(@RequestBody _Login login, HttpServletResponse response) throws NoSuchAlgorithmException {
        User user =  new JPAQuery<User>(entityManager).from(QUser.user)
                .where(QUser.user.login.eq(login.getLogin())
                        .and(QUser.user.password.eq(MD5.encrypt(login.getPassword()))))
                .fetchOne();
        String token;
        if (user != null) {
            token = (String) entityManager.createNativeQuery("select generate_token(?1) from dual").setParameter(1,user.getUuid()).getSingleResult();
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        response.setHeader("auth-token", token);
        return ResponseEntity.ok(new Wrapper(new _User(user)));
    }

}
