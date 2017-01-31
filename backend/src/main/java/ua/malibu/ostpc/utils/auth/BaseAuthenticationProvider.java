package ua.malibu.ostpc.utils.auth;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.services.UserService;

@Component
public class BaseAuthenticationProvider implements AuthenticationProvider {
    protected static final Logger logger = Logger.getLogger(BaseAuthenticationProvider.class);

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        LoginToken loginToken = (LoginToken) authentication;

        User user = userService.getUser((String)loginToken.getPrincipal());
        if (user == null) {
            logger.info("User with uuid " + loginToken.getPrincipal() + " was not found.");
            throw new RestException(HttpStatus.NOT_FOUND, 40004, "User " + loginToken.getPrincipal() + " not found.");
        }
        loginToken.setAuthenticatedUser(user);
        return loginToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LoginToken.class.isAssignableFrom(authentication);
    }
}
