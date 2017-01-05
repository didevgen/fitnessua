package ua.malibu.ostpc.controllers;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.dtos.auth.Wrapper;
import ua.malibu.ostpc.dtos.auth._User;
import ua.malibu.ostpc.enums.UserRole;
import ua.malibu.ostpc.models.auth.QToken;
import ua.malibu.ostpc.models.auth.QUser;
import ua.malibu.ostpc.models.auth.Token;
import ua.malibu.ostpc.models.auth.User;
import ua.malibu.ostpc.utils.MD5;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
public class UserController {

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("/api/baseurl")
    public User mainMethod() throws NoSuchAlgorithmException {
//        User u = new User();
//        u.setFullName("Eugene Kovaljov");
//        u.setLogin("didevgen@gmail.com");
//        u.setPassword(MD5.encrypt("didevgen"));
//        u.setUserRole(UserRole.ADMIN);
//        entityManager.persist(u);
//        User u1 = new User();
//        u1.setLogin("didevgen1@gmail.com");
//        u1.setFullName("Eugene Kovaljov");
//        u1.setPassword(MD5.encrypt("didevgen"));
//        u1.setUserRole(UserRole.USER);
//        entityManager.persist(u1);
//        return u;
        return null;
    }

    @Transactional
    @RequestMapping("/api/user")
    public ResponseEntity getUsers(HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        boolean tokenExists = new JPAQuery<Token>(entityManager).from(QToken.token)
                .where(QToken.token.value.eq(token).and(QToken.token.expires.gt(new Date(System.currentTimeMillis()))))
                .fetchCount() != 0;
        if (tokenExists) {
            List<User> users = new JPAQuery<User>(entityManager).from(QUser.user).fetchAll().fetch();
            List<_User> result = new ArrayList<>();
            users.forEach(item -> {
                result.add(new _User(item));
            });
            return ResponseEntity.ok(new Wrapper(users));
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/user/{uuid}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable String uuid, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        boolean tokenExists = new JPAQuery<Token>(entityManager).from(QToken.token)
                .where(QToken.token.value.eq(token).and(QToken.token.expires.gt(new Date(System.currentTimeMillis()))))
                .fetchCount() != 0;
        if (tokenExists) {
            entityManager.merge(user);
            return ResponseEntity.ok(new Wrapper(new _User(user)));
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/user/{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String uuid, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        boolean tokenExists = new JPAQuery<Token>(entityManager).from(QToken.token)
                .where(QToken.token.value.eq(token).and(QToken.token.expires.gt(new Date(System.currentTimeMillis()))))
                .fetchCount() != 0;
        User user = entityManager.find(User.class, uuid);
        if (tokenExists) {
            new JPADeleteClause(entityManager, QUser.user).where(QUser.user.uuid.eq(uuid)).execute();
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user, HttpServletRequest request) {
        String token = request.getHeader("auth-token");
        boolean tokenExists = new JPAQuery<Token>(entityManager).from(QToken.token)
                .where(QToken.token.value.eq(token).and(QToken.token.expires.gt(new Date(System.currentTimeMillis()))))
                .fetchCount() != 0;
        if (tokenExists) {
            user.setUserRole(UserRole.USER);
            entityManager.persist(user);
            return ResponseEntity.ok(new Wrapper(new _User(user)));
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @ExceptionHandler({JpaSystemException.class})
    public ResponseEntity sdfkgshjfh(Exception ex) {
        if (ex.getCause() instanceof GenericJDBCException) {
            GenericJDBCException exception = (GenericJDBCException) ex.getCause();
            if (exception.getErrorCode() == 20001) {
                return new ResponseEntity(new Wrapper("User with such email already exists"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
