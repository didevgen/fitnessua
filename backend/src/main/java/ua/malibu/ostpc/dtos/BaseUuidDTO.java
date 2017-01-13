package ua.malibu.ostpc.dtos;

import ua.malibu.ostpc.models.ITransferable;

public class BaseUuidDTO implements IDto<BaseUuidDTO>{

    private String uuid;

    @Override
    public BaseUuidDTO convert(ITransferable object) {
        return null;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
