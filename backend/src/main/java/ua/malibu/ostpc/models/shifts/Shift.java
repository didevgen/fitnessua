package ua.malibu.ostpc.models.shifts;

import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.users.User;
import ua.malibu.ostpc.models.workday.Workday;

public class Shift extends BaseEntity {

    private Workday workingDay;

    private Byte shiftOrdinal;

    private User wokerOnShift;

    public byte getShiftOrdinal() {
        return shiftOrdinal;
    }

    public void setShiftOrdinal(byte shiftOrdinal) {
        this.shiftOrdinal = shiftOrdinal;
    }

    public Workday getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(Workday workingDay) {
        this.workingDay = workingDay;
    }

    public User getWokerOnShift() {
        return wokerOnShift;
    }

    public void setWokerOnShift(User wokerOnShift) {
        this.wokerOnShift = wokerOnShift;
    }
}
