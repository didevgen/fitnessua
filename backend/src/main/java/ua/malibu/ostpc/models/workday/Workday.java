package ua.malibu.ostpc.models.workday;

import org.joda.time.DateTime;
import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.shifts.Shift;
import java.util.List;

public class Workday extends BaseEntity {

    private Long clubID;

    private Long scheduleID;

    private DateTime date;

    private List<Shift> shiftList;

    public Long getClubID() {
        return clubID;
    }

    public void setClubID(Long clubID) {
        this.clubID = clubID;
    }

    public Long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
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
