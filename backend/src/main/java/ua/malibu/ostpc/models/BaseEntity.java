package ua.malibu.ostpc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.malibu.ostpc.models.annotations.CloneIgnoreField;

import javax.persistence.*;
import java.io.Serializable;

/**
    Designates a class whose mapping information is applied to the
    entities that inherit from it. A mapped superclass has no separate table defined for it.
*/
@MappedSuperclass
public class BaseEntity implements IIndexed, Serializable{

    @CloneIgnoreField
    private static final long serialVersionUID = 2855346562216652344L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
