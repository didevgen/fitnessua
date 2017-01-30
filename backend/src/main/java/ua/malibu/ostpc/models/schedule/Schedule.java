package ua.malibu.ostpc.models.schedule;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.malibu.ostpc.enums.ScheduleStatus;
import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.users.User;
import ua.malibu.ostpc.models.workday.Workday;
import ua.malibu.ostpc.models.UUIDEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Schedule extends BaseEntity {

    //    @Column(name="start_date")
//    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @Transient
    private DateTime startDate;

    /*@Column(name="end_date")
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")*/
    @Transient
    private DateTime endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @OneToMany(mappedBy = "schedule")
    private List<Workday> workdayList;

    public Schedule() {
    }

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

    public List<Workday> getWorkdayList() {
        return workdayList;
    }

    public void setWorkdayList(List<Workday> workdayList) {
        this.workdayList = workdayList;
    }

}