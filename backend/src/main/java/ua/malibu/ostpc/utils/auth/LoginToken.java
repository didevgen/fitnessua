package ua.malibu.ostpc.utils.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

public class LoginToken extends UsernamePasswordAuthenticationToken {
    private String uuid;

    public LoginToken(String login, String pass, String uuid) {
        super(login, pass, Collections.EMPTY_LIST);
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }
}