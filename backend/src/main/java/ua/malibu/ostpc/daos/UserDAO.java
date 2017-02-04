package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.models.QUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("userDao")
@Transactional
public class UserDAO implements IDAO<User>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        entityManager.detach(user);
    }

    @Override
    public User get(String uuid) {
        return new JPAQuery<User>(entityManager).from(QUser.user).where(QUser.user.uuid.eq(uuid)).fetchOne();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
