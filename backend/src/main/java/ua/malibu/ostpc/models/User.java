package ua.malibu.ostpc.models;

import javax.persistence.*;
import java.util.Date;
import ua.malibu.ostpc.enums.Roles;

@Entity
@Table(name="Users")
public class User extends UUIDEntity {
    @Column(name="Name", nullable=false)
    private String name;

    @Column(name="Surname", nullable=false)
    private String surname;

    @Column(name="MiddleName")
    private String middleName;

    @Column(name="Birthday")
    @Temporal(value=TemporalType.DATE)
    private Date birthday;

    @Column(name="Address")
    private String address;

    @Column(name="Email")
    private String email;

    @Column(name="PhoneNumber")
    private String phoneNumber;

    @Column(name="Role")
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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
