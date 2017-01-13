package ua.malibu.ostpc.models.club;

import ua.malibu.ostpc.models.BaseEntity;
import ua.malibu.ostpc.models.UUIDEntity;

import javax.persistence.*;

@Entity
@Table(name="clubs")
public class Club extends BaseEntity {
    @Column(name="title")
    private String title;

    public Club() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
