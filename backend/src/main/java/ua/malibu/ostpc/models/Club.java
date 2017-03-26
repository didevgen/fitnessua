package ua.malibu.ostpc.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.joda.time.DateTime;
import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="clubs")
public class Club extends BaseEntity implements Comparable<Club>{
    @Column(name="title")
    private String title;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<WorkDay> workingDays = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = {CascadeType.ALL})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Holiday> holidays = new ArrayList<>();

    @OneToOne(fetch=FetchType.LAZY, mappedBy="club")
    private ClubPreference preference;

    @Column(name="shifts_quantity")
    private Integer shiftsQuantity;

    @Column(name="shift_duration")
    private Integer shiftDuration;

    @Column(name="address")
    private String address;

    public Club() {}

    @Override
    public int compareTo(Club o) {
        if (this.getId().equals(o.getId())) {
            return 0;
        } else if (this.getId().longValue() > o.getId().longValue()) {
            return 1;
        } else return -1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WorkDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<WorkDay> workingDays) {
        this.workingDays = workingDays;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public ClubPreference getPreference() {
        return preference;
    }

    public void setPreference(ClubPreference preference) {
        this.preference = preference;
    }

    public boolean isHoliday(DateTime holiday) {
        return holidays.stream().anyMatch(h -> h.getDate().isEqual(holiday));
    }

    public Integer getShiftsQuantity() {
        return shiftsQuantity;
    }

    public void setShiftsQuantity(Integer shiftsQuantity) {
        this.shiftsQuantity = shiftsQuantity;
    }

    public Integer getShiftDuration() {
        return shiftDuration;
    }

    public void setShiftDuration(Integer shiftDuration) {
        this.shiftDuration = shiftDuration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Club other = (Club) obj;
        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
}
