package ua.malibu.ostpc.models;

import javax.persistence.*;

@Entity
@Table(name="Clubs")
public class Club extends UUIDEntity {
    @Column(name="Title")
    private String title;

    public Club() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
