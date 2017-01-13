package ua.malibu.ostpc.models.users;

import javax.persistence.*;
import java.util.Date;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.malibu.ostpc.enums.Roles;
import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.UUIDEntity;

@Entity
@Table(name="users")
public class User extends BaseEntity {

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="surname", nullable=false)
    private String surname;

    @Column(name="middle_name")
    private String middleName;

    @Column(name="birthday")
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime birthday;

    @Column(name="address")
    private String address;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Roles role;

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public DateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
