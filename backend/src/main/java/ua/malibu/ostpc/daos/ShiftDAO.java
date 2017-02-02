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
public class ShiftDAO implements IDAO<Shift> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(Shift shift) {
        entityManager.persist(shift);
    }

    @Override
    public void update(Shift shift) {
        entityManager.merge(shift);
    }

    @Override
    public void delete(Shift shift) {
        entityManager.detach(shift);
    }

    @Override
    public Shift get(String uuid) {
        return new JPAQuery<Shift>(entityManager).from(QShift.shift).where(QShift.shift.uuid.eq(uuid)).fetchOne();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
