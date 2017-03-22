package ua.malibu.ostpc.models;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.malibu.ostpc.enums.UserRole;
import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "min_working_time")
    private Long minWorkingTime;

    @Column(name = "max_working_time")
    private Long maxWorkingTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_shifts",
            joinColumns = @JoinColumn,
            inverseJoinColumns = @JoinColumn)
    private List<Shift> shifts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy="users", fetch = FetchType.LAZY)
    private List<ClubPreference> preferences = new ArrayList<>();

    public User() {
    }

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ClubPreference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<ClubPreference> preferences) {
        this.preferences = preferences;
    }

    public Long getMinWorkingTime() {
        return minWorkingTime;
    }

    public void setMinWorkingTime(Long minWorkingTime) {
        this.minWorkingTime = minWorkingTime;
    }

    public Long getMaxWorkingTime() {
        return maxWorkingTime;
    }

    public void setMaxWorkingTime(Long maxWorkingTime) {
        this.maxWorkingTime = maxWorkingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (!getName().equals(user.getName())) return false;
        if (!getSurname().equals(user.getSurname())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        return getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}
