package ua.malibu.ostpc.controllers.test;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
        user.setBirthday(new DateTime(1, 2, 3, 4, 5));
        user.setEmail("qwe");
        user.setPassword(MD5.encrypt("rty"));
        userService.saveUser(user);
        User user2 = new User();
        user2.setSurname("ivanov");
        user2.setName("ivan");
        user2.setBirthday(new DateTime(1, 2, 3, 4, 5));
        user2.setEmail("max_usmambkjbghntjlgfekovskii@rambler.com");
        user2.setPassword(MD5.encrypt("rty"));
        userService.saveUser(user2);
        User user3 = new User();
        user3.setSurname("Усмамбdеков htjntrj j j b j gkegjhtrgjkhtj g  fs g sfd  dfc sgs s s");
        user3.setName("Варфоломей");
        user3.setPhoneNumber("wqeq341234fed");
        user3.setBirthday(new DateTime(1985, 12, 3, 4, 5));
        user3.setEmail("anton-zaporozhchenko@rambler.com");
        user3.setPassword(MD5.encrypt("rty"));
        userService.saveUser(user3);

        Club club1 = new Club(), club2 = new Club(), club3 = new Club();
        WorkDay w1 = new WorkDay(), w2 = new WorkDay(), w3 = new WorkDay(), w4 = new WorkDay();
        club1.setId(1l);
        club2.setId(2l);
        club3.setId(3l);
        club1.setTitle("title1");
        club2.setTitle("title2");
        club3.setTitle("title3");
        club1.setShiftDuration(7);
        club2.setShiftDuration(8);
        club3.setShiftDuration(8);
        club1.setAddress("Улица пупшкина дом колотушкина");
        club2.setAddress("Площадь твоей выебанной мамаши, дом 5");
        club3.setAddress("Город москва улица красная площадь 123 номер дома не знаю");
        w1.setDate(DateTime.now().minusDays(5));
        w2.setDate(DateTime.now());
        w3.setDate(DateTime.now().plusDays(1));
        w4.setDate(DateTime.now().minusDays(2));
        w1.setClub(club1);
        w2.setClub(club3);
        w3.setClub(club2);
        w4.setClub(club2);
        Shift sh1 = new Shift(), sh2 = new Shift(), sh3 = new Shift(), sh4 = new Shift();
        sh1.setWorkingDay(w1);
        sh1.setWorkersOnShift(Arrays.asList(user));
        w1.setSchedule(schedule);

        sh2.setWorkingDay(w1);
        sh2.setWorkersOnShift(Arrays.asList(user, user2, user3));
        w1.setShifts(Arrays.asList(sh1, sh2));

        sh3.setWorkingDay(w2);
        sh3.setWorkersOnShift(Arrays.asList(user, user3));
        w2.setShifts(Arrays.asList(sh3));
        w2.setSchedule(schedule);
        w3.setSchedule(schedule);

        sh4.setWorkingDay(w4);
        sh4.setWorkersOnShift(Arrays.asList(user2));
        w4.setShifts(Arrays.asList(sh4));
        w4.setSchedule(schedule);
        schedule.setWorkingDays(Arrays.asList(w1, w2, w3, w4));
        schedule.setId(876l);
        schedule.setUuid("1");
        schedule.setStatus(ScheduleStatus.CURRENT);
        ReportGenerator reportGenerator = new ReportGenerator(schedule);
        reportGenerator.generateReport(DateTime.now().minusDays(4), DateTime.now());
        return new ResponseEntity(HttpStatus.OK);
    }

}
