package ua.malibu.ostpc.models.schedule;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.malibu.ostpc.enums.ScheduleStatus;
import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.UUIDEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Schedule extends BaseEntity {

    @Column(name="start_date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startDate;

    @Column(name="end_date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endDate;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public Schedule() {}

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }
}
