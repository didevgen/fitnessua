package ua.malibu.ostpc.models.shifts;

import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.users.User;
import ua.malibu.ostpc.models.workday.Workday;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shifts")
public class Shift extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "workday_id")
    private Workday workingDay;

    @Column(name = "shift_ordinal")
    private Integer shiftOrdinal;

    @ManyToMany
    @JoinTable(name = "users_shifts",
               joinColumns = @JoinColumn(name = "shift_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> workersOnShift;

    public int getShiftOrdinal() {
        return shiftOrdinal;
    }

    public void setShiftOrdinal(int shiftOrdinal) {
        this.shiftOrdinal = shiftOrdinal;
    }

    public Workday getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(Workday workingDay) {
        this.workingDay = workingDay;
    }

    public List<User> getWokerOnShift() {
        return workersOnShift;
    }

    public void setWokerOnShift(List<User> wokerOnShift) {
        this.workersOnShift = wokerOnShift;
    }
}
