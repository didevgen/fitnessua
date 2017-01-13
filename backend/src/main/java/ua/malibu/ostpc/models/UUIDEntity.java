package ua.malibu.ostpc.models;

import com.google.common.base.Objects;
import ua.malibu.ostpc.models.annotations.CloneIgnoreField;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class UUIDEntity extends BaseEntity {

    @CloneIgnoreField
    private static final long serialVersionUID = -8466770986304062624L;

    @Column(nullable = false, unique = true)
    @CloneIgnoreField
    private String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final UUIDEntity other = (UUIDEntity) obj;
        return Objects.equal(this.uuid, other.getUuid())
                && Objects.equal(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId(), this.uuid);
    }
}
