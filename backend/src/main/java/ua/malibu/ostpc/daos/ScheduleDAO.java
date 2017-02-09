package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import ua.malibu.ostpc.models.QSchedule;
import ua.malibu.ostpc.models.Schedule;

/**
 * Created by Игорь on 09.02.2017.
 */
public class ScheduleDAO extends BaseDAO<Schedule> {
    @Override
    public Schedule get(String uuid) {
        return new JPAQuery<Schedule>(entityManager).from(QSchedule.schedule).where(QSchedule.schedule.uuid.eq(uuid)).fetchOne();
    }
}
