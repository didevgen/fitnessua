package ua.malibu.ostpc.utils;

import ua.malibu.ostpc.models.*;

import java.util.*;

public class ReportUnit {
    private Map<Club, Integer> shifts = new HashMap<>();

    public void addShift(Club club) {
        shifts.computeIfPresent(club, (key, value) -> value += 1);
        shifts.putIfAbsent(club, 1);
    }

    public Map<Club, Integer> getShifts() {
        return shifts;
    }

}
