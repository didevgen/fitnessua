package ua.malibu.ostpc.utils.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import ua.malibu.ostpc.models.User;

import java.util.Collections;

public class LoginToken extends AbstractAuthenticationToken {
    private String login;
    private String password;
    private User authenticatedUser;

    public LoginToken(String uuid) {
        super(Collections.EMPTY_LIST);
        this.login = uuid;
    }

    @Override
    public Object getPrincipal() {
        return login;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }
}
