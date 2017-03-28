package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import ua.malibu.ostpc.models.Draft;
import ua.malibu.ostpc.models.QDraft;

public class DraftDAO extends BaseDAO<Draft> {

    @Override
    public Draft get(String uuid) {
        return new JPAQuery<Draft>(entityManager).from(QDraft.draft).where(QDraft.draft.uuid.eq(uuid)).fetchOne();
    }
}
