package ua.malibu.ostpc.models;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.malibu.ostpc.models.base.BaseEntity;
import ua.malibu.ostpc.models.Club;
import ua.malibu.ostpc.models.Schedule;
import ua.malibu.ostpc.models.Shift;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "work_day")
public class WorkDay extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @OneToMany(mappedBy = "workingDay", cascade = CascadeType.ALL)
    private List<Shift> shifts;

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }
}
