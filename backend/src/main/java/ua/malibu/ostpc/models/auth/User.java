package ua.malibu.ostpc.models.auth;

import org.hibernate.annotations.GenericGenerator;
import ua.malibu.ostpc.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name="my_user")
public class User {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid")
    @Column(name="user_uuid")
    private String uuid;

    @Column(name = "login")
    private String login;

    @Column
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name="full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
