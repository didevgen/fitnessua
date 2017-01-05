package ua.malibu.ostpc.dtos.auth;

import ua.malibu.ostpc.enums.UserRole;
import ua.malibu.ostpc.models.auth.User;

public class _User {

    private String uuid;

    private String email;

    private String fullName;

    private String userRole;

    public _User(String uuid, String fullName, String userRole, String email) {
        this.fullName = fullName;
        this.uuid = uuid;
        this.userRole = userRole;
        this.email = email;
    }

    public _User(User user) {
        this.fullName = user.getFullName();
        this.uuid = user.getUuid();
        if (user.getUserRole() != null)
            this.userRole = user.getUserRole().name();
        else {
            this.userRole = UserRole.USER.name();
        }
        this.email = user.getEmail();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
