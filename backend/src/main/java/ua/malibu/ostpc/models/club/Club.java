package ua.malibu.ostpc.models.club;

import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.UUIDEntity;
import ua.malibu.ostpc.models.workday.Workday;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="clubs")
public class Club extends BaseEntity {
    @Column(name="title")
    private String title;

    @OneToMany(mappedBy = "club")
    private List<Workday> clubWorkdays;

    public Club() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Workday> getClubWorkdays() {
        return clubWorkdays;
    }

    public void setClubWorkdays(List<Workday> clubWorkdays) {
        this.clubWorkdays = clubWorkdays;
    }
}
