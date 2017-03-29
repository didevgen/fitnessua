package ua.malibu.ostpc.daos;

import com.querydsl.jpa.impl.JPAQuery;
import ua.malibu.ostpc.models.EmployeePreference;
import ua.malibu.ostpc.models.QEmployeePreference;

public class EmployeePreferenceDAO extends BaseDAO<EmployeePreference> {

    @Override
    public EmployeePreference get(String uuid) {
        return new JPAQuery<EmployeePreference>(entityManager)
                .from(QEmployeePreference.employeePreference)
                .where(QEmployeePreference.employeePreference.uuid.eq(uuid)).fetchOne();
    }
}
