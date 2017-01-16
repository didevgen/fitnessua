package ua.malibu.ostpc.utils.auth;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.http.HttpStatus;
import ua.malibu.ostpc.dtos.auth._Login;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.users.QUser;
import ua.malibu.ostpc.models.users.User;
import ua.malibu.ostpc.utils.MD5;

import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class Verifier {
    protected static final transient Logger logger = Logger.getLogger(Verifier.class.getName());

    public User VerifyCreds(_Login creds, EntityManager entityManager) {
        try {
            User user = new JPAQuery<User>(entityManager).from(QUser.user)
                    .where(QUser.user.email.eq(creds.getLogin())
                            .and(QUser.user.password.eq(MD5.encrypt(creds.getPassword()))))
                    .fetchOne();
            if (user == null) {
                logger.info("User with login " + creds.getLogin() + " password " + creds.getPassword() + " is not found");
                throw new RestException(HttpStatus.NOT_FOUND, 404, "user not found");
            }
            return user;
        } catch (NoSuchAlgorithmException e) {
            logger.fatal("Encryption algorithm not found. Credentials cannot be verified", e);
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "encryption algorithm error");
        }
    }
}
