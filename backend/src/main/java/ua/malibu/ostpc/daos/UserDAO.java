package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.models.QUser;
import ua.malibu.ostpc.models.User;

@Repository("userDao")
@Transactional
public class UserDAO extends BaseDAO<User>{
    @Override
    public User get(String uuid) {
        return new JPAQuery<User>(entityManager).from(QUser.user).where(QUser.user.uuid.eq(uuid)).fetchOne();
    }
}
