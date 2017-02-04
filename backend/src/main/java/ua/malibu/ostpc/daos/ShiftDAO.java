package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.models.QShift;
import ua.malibu.ostpc.models.Shift;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Игорь on 31.01.2017.
 */
@Repository("shiftDao")
@Transactional
public class ShiftDAO extends BaseDAO<Shift> {
    @Override
    public Shift getById(String uuid) {
        return new JPAQuery<Shift>(entityManager).from(QShift.shift).where(QShift.shift.uuid.eq(uuid)).fetchOne();
    }

}
