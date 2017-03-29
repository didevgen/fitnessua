package ua.malibu.ostpc.dtos.employeePreferenceDTO;

import ua.malibu.ostpc.dtos.BaseUuidDTO;
import ua.malibu.ostpc.models.EmployeePreference;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeePreferenceDTO extends BaseUuidDTO <EmployeePreference> {
    private String user;
    private List<String> clubs;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getClubs() {
        return clubs;
    }

    public void setClubs(List<String> clubs) {
        this.clubs = clubs;
    }

    public EmployeePreferenceDTO convert(EmployeePreference object) {
        this.setUser(object.getUser().getUuid());
        this.setClubs(object.getClubs().stream().map(emplPreference -> emplPreference.getUuid()).collect(Collectors.toList()));
        return this;
    }

}
