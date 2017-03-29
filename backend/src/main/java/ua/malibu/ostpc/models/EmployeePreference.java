package ua.malibu.ostpc.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "empl_prefs")
public class EmployeePreference extends BaseEntity{

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_preferenced_clubs",
            joinColumns = @JoinColumn,
            inverseJoinColumns = @JoinColumn)
    private List<Club> clubs = new ArrayList<Club>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
}
