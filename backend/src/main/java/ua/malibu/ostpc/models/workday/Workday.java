package ua.malibu.ostpc.models.workday;

import org.joda.time.DateTime;
import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.club.Club;
import ua.malibu.ostpc.models.schedule.Schedule;
import ua.malibu.ostpc.models.shifts.Shift;

import javax.persistence.*;
import java.util.List;

@Entity
public class Workday extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "date")
    private DateTime date;

    @OneToMany(mappedBy = "workingDay")
    private List<Shift> shiftList;

    public Club getClubID() {
        return club;
    }

    public void setClubID(Club club) {
        this.club = club;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setScheduleID(Schedule schedule) {
        this.schedule = schedule;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }
}
