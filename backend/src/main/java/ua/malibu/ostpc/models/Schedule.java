package ua.malibu.ostpc.models;

import ua.malibu.ostpc.enums.ScheduleStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Schedule extends UUIDEntity {
    @ManyToOne
    @JoinColumn(name="id")
    private Long clubId;

    @Column(name="StartDate")
    @Temporal(value=TemporalType.DATE)
    private Date startDate;

    @Column(name="EndDate")
    @Temporal(value=TemporalType.DATE)
    private Date endDate;

    @Column(name="Status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public Schedule() {}

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }
}
