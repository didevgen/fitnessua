package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import ua.malibu.ostpc.models.Club;
import ua.malibu.ostpc.models.QClub;

public class ClubDAO extends BaseDAO<Club> {
    @Override
    public Club get(String uuid) {
        return new JPAQuery<Club>(entityManager)
                .from(QClub.club)
                .where(QClub.club.uuid.eq(uuid))
                .fetchOne();
    }

}
