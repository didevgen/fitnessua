package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.models.ClubPreference;
import ua.malibu.ostpc.models.QClubPreference;

/**
 * Created by Игорь on 09.02.2017.
 */
@Repository("clubPreferenceDao")
@Transactional
public class ClubPreferenceDAO extends BaseDAO<ClubPreference> {
    @Override
    public ClubPreference get(String uuid) {
        return new JPAQuery<ClubPreference>(entityManager)
                .from(QClubPreference.clubPreference)
                .where(QClubPreference.clubPreference.uuid.eq(uuid)).fetchOne();
    }
}
