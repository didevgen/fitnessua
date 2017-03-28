package ua.malibu.ostpc.controllers.test;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.malibu.ostpc.daos.ClubDAO;
import ua.malibu.ostpc.daos.ScheduleDAO;
import ua.malibu.ostpc.enums.ScheduleStatus;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.*;
import ua.malibu.ostpc.services.UserService;
import ua.malibu.ostpc.utils.MD5;
import ua.malibu.ostpc.utils.ReportGenerator;

import java.io.IOException;
import java.util.*;

@RestController
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleDAO scheduleDAO;
    @Autowired
    private ClubDAO clubDAO;

    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<?> test() {
        throw new RestException(HttpStatus.BAD_REQUEST, 400001, "You sucked");
    }

    @RequestMapping(value = "/test/{testId}", method = RequestMethod.GET)
    public void tes1(@PathVariable(name = "testId") Integer testId) throws IOException {
    }

    @Transactional
    @RequestMapping("/blah")
    public ResponseEntity someMethod() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setStatus(ScheduleStatus.CURRENT);
        schedule.setStartDate(DateTime.now().minusDays(5));
        schedule.setEndDate(DateTime.now().plusDays(2));
        User user = new User();
        user.setSurname("petrov");
        user.setName("petr");
        user.setId(1l);
        user.setBirthday(new DateTime(1, 2, 3, 4, 5));
        user.setEmail("qwe");
        user.setPassword(MD5.encrypt("rty"));
        User user3 = new User();
        user3.setSurname("Усмамбdеков htjntrj j j b j gkegjhtrgjkhtj g  fs g sfd  dfc sgs s s");
        user3.setName("Варфоломей");
        user3.setId(2l);
        user3.setPhoneNumber("wqeq341234fed");
        user3.setBirthday(new DateTime(1985, 12, 3, 4, 5));
        user3.setEmail("anton-zaporozhchenko@rambler.com");
        user3.setPassword(MD5.encrypt("rty"));
        User user2 = new User();
        user2.setSurname("ivanov");
        user2.setName("ivan");
        user2.setId(3l);
        user2.setBirthday(new DateTime(1, 2, 3, 4, 5));
        user2.setEmail("       Улица пупшкkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkина дом колотушкина@rambler.com");
        user2.setPassword(MD5.encrypt("rty"));

        Club club1 = new Club(), club2 = new Club(), club3 = new Club(),
        club4 = new Club(), club5 = new Club(), club6 = new Club(), club7 = new Club(), club8 = new Club();
        WorkDay w1 = new WorkDay(), w2 = new WorkDay(), w3 = new WorkDay(), w4 = new WorkDay(),
        w5 = new WorkDay(), w6 = new WorkDay(), w7 = new WorkDay(), w8 = new WorkDay();
        club1.setTitle("title1");
        club2.setTitle("title2");
        club3.setTitle("title3");
        club4.setTitle("title4");
        club5.setTitle("title5");
        club6.setTitle("title6");
        club7.setTitle("title7");
        club8.setTitle("title8");
        club1.setShiftDuration(7);
        club2.setShiftDuration(8);
        club3.setShiftDuration(8);
        club4.setShiftDuration(7);
        club5.setShiftDuration(8);
        club6.setShiftDuration(8);
        club7.setShiftDuration(7);
        club8.setShiftDuration(8);
        club1.setAddress("Улица пупшкkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkина дом колотушкина");
        club2.setAddress("Улица пупшкkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkина дом колотушкина");
        club3.setAddress("Улица пупшкина дом колотушкина");
        club4.setAddress("Улица пупшкkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkина дом колотушкина");
        club5.setAddress("Улица пупшкkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkина дом колотушкина");
        club6.setAddress("Улица пупшкkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkина дом колотушкина");
        club7.setAddress("Улица пупшкkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkина дом колотушкина");
        club8.setAddress("Улица пупшкkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkина дом колотушкина");
        w1.setDate(DateTime.now().minusDays(5));
        w2.setDate(DateTime.now());
        w3.setDate(DateTime.now().plusDays(1));
        w4.setDate(DateTime.now().minusDays(2));
        w5.setDate(DateTime.now().minusDays(2));
        w6.setDate(DateTime.now().minusDays(2));
        w7.setDate(DateTime.now().minusDays(2));
        w8.setDate(DateTime.now().minusDays(2));

        w1.setClub(club1);
        w2.setClub(club3);
        w3.setClub(club2);
        w4.setClub(club4);
        w5.setClub(club5);
        w6.setClub(club6);
        w7.setClub(club7);
        w8.setClub(club8);
        Shift sh1 = new Shift(), sh2 = new Shift(), sh3 = new Shift(), sh4 = new Shift(),
        sh5 = new Shift(), sh6 = new Shift(), sh7 = new Shift(), sh8 = new Shift();
        sh1.setWorkingDay(w1);
        sh1.setWorkersOnShift(Arrays.asList(user));
        w1.setSchedule(schedule);
        sh4.setWorkersOnShift(Arrays.asList(user));
        sh4.setWorkingDay(w1);


        sh5.setWorkingDay(w5);
        sh5.setWorkersOnShift(Arrays.asList(user));
        w5.setSchedule(schedule);
        w5.setShifts(Arrays.asList(sh5));

        sh6.setWorkingDay(w6);
        sh6.setWorkersOnShift(Arrays.asList(user));
        w6.setSchedule(schedule);
        w6.setShifts(Arrays.asList(sh6));

        sh7.setWorkingDay(w7);
        sh7.setWorkersOnShift(Arrays.asList(user));
        w7.setSchedule(schedule);
        w7.setShifts(Arrays.asList(sh7));

        sh8.setWorkingDay(w8);
        sh8.setWorkersOnShift(Arrays.asList(user));
        w8.setSchedule(schedule);
        w8.setShifts(Arrays.asList(sh8));

        sh2.setWorkingDay(w1);
        sh2.setWorkersOnShift(Arrays.asList(user, user3));
        w1.setShifts(Arrays.asList(sh1, sh2, sh4));

        sh3.setWorkingDay(w2);
        sh3.setWorkersOnShift(Arrays.asList(user, user2, user3));
        w2.setShifts(Arrays.asList(sh3));
        w2.setSchedule(schedule);


        w3.setSchedule(schedule);

        club1.setId(1l);
        club2.setId(2l);
        club3.setId(3l);
        club4.setId(4l);
        club5.setId(5l);
        club6.setId(6l);
        club7.setId(7l);
        club8.setId(8l);
        sh4.setWorkingDay(w4);
        sh4.setWorkersOnShift(Arrays.asList( user, user3));
        w4.setShifts(Arrays.asList(sh4));
        w4.setSchedule(schedule);
        schedule.setWorkingDays(Arrays.asList(w1, w2, w3, w4, w5, w6, w7 ,w8));
        schedule.setUuid("1");
        ReportGenerator reportGenerator = new ReportGenerator(schedule);
        reportGenerator.generateReport();
        return new ResponseEntity(HttpStatus.OK);
    }

}
