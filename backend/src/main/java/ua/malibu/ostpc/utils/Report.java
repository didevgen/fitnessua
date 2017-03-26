package ua.malibu.ostpc.utils;

import org.joda.time.DateTime;
import ua.malibu.ostpc.enums.ScheduleStatus;
import ua.malibu.ostpc.models.User;

import java.util.Map;

public class Report {
    private DateTime startTime;
    private DateTime endTime;
    private ScheduleStatus status;
    private Map<User, ReportUnit> reportUnits;

    public Map<User, ReportUnit> getReportUnits() {
        return reportUnits;
    }

    public void setReportUnits(Map<User, ReportUnit> reportUnits) {
        this.reportUnits = reportUnits;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public ScheduleStatus getStatus() {
        return status;
    }
}
