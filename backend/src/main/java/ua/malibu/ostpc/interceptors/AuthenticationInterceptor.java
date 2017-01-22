package ua.malibu.ostpc.interceptors;

import com.google.gson.Gson;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ua.malibu.ostpc.dtos.auth._Login;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.users.QUser;
import ua.malibu.ostpc.models.users.User;
import ua.malibu.ostpc.utils.MD5;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.*;
import java.security.NoSuchAlgorithmException;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    protected static final transient Logger logger = Logger.getLogger(AuthenticationInterceptor.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor");
        Gson gson = new Gson();
        _Login creds =  gson.fromJson(request.getReader(), _Login.class);
        try {
            if (new JPAQuery<User>(entityManager).from(QUser.user)
                    .where(QUser.user.email.eq(creds.getLogin())
                            .and(QUser.user.password.eq(MD5.encrypt(creds.getPassword()))))
                    .fetchOne() == null) {
                logger.info("User with login \"" + creds.getLogin() + "\" password \"" + creds.getPassword() + "\" is not found");
                throw new RestException(HttpStatus.NOT_FOUND, 40004, "user not found");
            } else {
                logger.info("User with login \"" + creds.getLogin() + "\" password \"" + creds.getPassword() + "\" found");
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            logger.fatal("Encryption algorithm not found. Credentials cannot be verified", e);
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "encryption algorithm error");
        }
    }
}
