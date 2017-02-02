package ua.malibu.ostpc.dtos.shift;

import ua.malibu.ostpc.dtos.IDto;
import ua.malibu.ostpc.models.Shift;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.models.WorkDay;
import java.util.List;

/**
 * Created by Игорь on 01.02.2017.
 */

public class ShiftDTO  implements IDto<Shift> {
    private WorkDay workingDay;
    private Integer shiftOrdinal;
    private List<User> workersOnShift;

    public WorkDay getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(WorkDay workingDay) {
        this.workingDay = workingDay;
    }

    public Integer getShiftOrdinal() {
        return shiftOrdinal;
    }

    public void setShiftOrdinal(Integer shiftOrdinal) {
        this.shiftOrdinal = shiftOrdinal;
    }

    public List<User> getWorkersOnShift() {
        return workersOnShift;
    }

    public void setWorkersOnShift(List<User> workersOnShift) {
        this.workersOnShift = workersOnShift;
    }

    @Override
    public ShiftDTO convert(Shift object) {
        this.setWorkingDay(object.getWorkingDay());
        this.setShiftOrdinal(object.getShiftOrdinal());
        this.setWorkersOnShift(object.getWorkersOnShift());
        return this;
    }
}
