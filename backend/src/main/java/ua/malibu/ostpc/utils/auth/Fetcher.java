package ua.malibu.ostpc.utils.auth;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.http.HttpStatus;
import ua.malibu.ostpc.dtos.auth._Login;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.QUser;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.utils.MD5;

import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class Fetcher {
    protected static final transient Logger logger = Logger.getLogger(Fetcher.class.getName());

    public User FetchUser(_Login creds, EntityManager entityManager) {
        try {
            return new JPAQuery<User>(entityManager).from(QUser.user)
                    .where(QUser.user.email.eq(creds.getLogin())
                            .and(QUser.user.password.eq(MD5.encrypt(creds.getPassword()))))
                    .fetchOne();
        } catch (NoSuchAlgorithmException e) {
            logger.fatal("Encryption algorithm not found. Credentials cannot be verified", e);
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "encryption algorithm error");
        }
    }
}
