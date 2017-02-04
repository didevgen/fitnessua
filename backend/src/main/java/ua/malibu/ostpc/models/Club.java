package ua.malibu.ostpc.models;

import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="clubs")
public class Club extends BaseEntity {
    @Column(name="title")
    private String title;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<WorkDay> workingDays;

    public Club() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WorkDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<WorkDay> workingDays) {
        this.workingDays = workingDays;
    }
}
