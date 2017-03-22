package ua.malibu.ostpc.utils;

import org.joda.time.DateTime;
import ua.malibu.ostpc.models.*;

import java.util.*;

/** Here to be the class for schedule generating */
public class ScheduleGenerator {
//    private DateTime startDate;
//    private DateTime endDate;
//    private List<ClubPreference> clubList;
//
//    public ScheduleGenerator(DateTime startDate, DateTime endDate, List<ClubPreference> clubList) {
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.clubList = clubList;
//    }
//
//    public Schedule generateSchedule() {
//        Schedule schedule = new Schedule();
//        Set<WorkDay> workDays = new TreeSet<>();
//        WorkDay curWorkDay;
//        DateTime curStartDate = new DateTime(startDate);
//        DateTime curEndDate = new DateTime(curStartDate.plusDays(6));
//        List<User> userList;
//        Map<User, List<DateTime>> usersDates = new HashMap<>();
//        Map<User, Integer> workingHours = new HashMap<>();
//        Club curClub;
//        User curUser;
//        Shift newShift;
//        int count;
//
//        schedule.setStartDate(startDate);
//        schedule.setEndDate(endDate);
//        while (curStartDate.isBefore(endDate)) {
//            if (curEndDate.isAfter(endDate)) {
//                curEndDate = curEndDate.withDate(endDate.toLocalDate());
//            }
//            for (ClubPreference curClubPrefs : clubList) {
//                userList = curClubPrefs.getUsers();
//                curClub = curClubPrefs.getClub();
//                count = 0;
//                int usersNumber = userList.size();
//                boolean isEmptyList = false;
//                do {
//                    if (curClub.isHoliday(curStartDate)) {
//                        curStartDate = curStartDate.plusDays(1);
//                        continue;
//                    }
//                    curUser = userList.get(((count++) % usersNumber));
//                    //if ()
//                    curWorkDay = new WorkDay();
//                    curWorkDay.setClub(curClub);
//                    curWorkDay.setDate(curStartDate);
//                    curWorkDay.setSchedule(schedule);
//                    for (int i = 0; i < curClub.getShiftsQuantity(); ++i) {
//                        newShift = new Shift();
//                        newShift.setShiftOrdinal(i + 1);
//                        newShift.getWorkersOnShift().add(curUser);
//                        newShift.setWorkingDay(curWorkDay);
//                        curWorkDay.getShifts().add(newShift);
//                        curUser.getShifts().add(newShift);
//                    }
//                    curStartDate = curStartDate.plusDays(1);
//                } while (curStartDate.isBefore(curEndDate)
//                        || curStartDate.isEqual(curEndDate));
//            }
//        }
//    }
}
