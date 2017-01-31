package ua.malibu.ostpc.dtos;

import ua.malibu.ostpc.models.base.UUIDEntity;

public class BaseUuidDTO implements IDto<UUIDEntity>{

    private String uuid;

    @Override
    public BaseUuidDTO convert(UUIDEntity object) {
        this.setUuid(object.getUuid());
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
