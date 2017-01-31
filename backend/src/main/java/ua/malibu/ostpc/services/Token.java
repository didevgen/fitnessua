package ua.malibu.ostpc.services;

public class Token {
    private String tokenValue;
    private String userUUID;

    public Token(String tokenValue, String userUUID) {
        this.tokenValue = tokenValue;
        this.userUUID = userUUID;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

}

