package ua.malibu.ostpc.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthToken extends UsernamePasswordAuthenticationToken{
    private String tokenValue;
    private String userUUID;

    public AuthToken(String tokenValue, String userUUID) {
        super(tokenValue, userUUID);
        this.tokenValue = tokenValue;
        this.userUUID = userUUID;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public String getUserUUID() {
        return userUUID;
    }
}

