package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import ua.malibu.ostpc.models.QWorkDay;
import ua.malibu.ostpc.models.WorkDay;

/**
 * Created by Игорь on 04.02.2017.
 */
public class WorkDayDAO extends BaseDAO<WorkDay> {
    @Override
    public WorkDay get(String uuid) {
        return new JPAQuery<WorkDay>(entityManager).from(QWorkDay.workDay).where(QWorkDay.workDay.uuid.eq(uuid)).fetchOne();
    }
}
