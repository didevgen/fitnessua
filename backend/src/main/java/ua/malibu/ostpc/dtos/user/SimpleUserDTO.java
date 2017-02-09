package ua.malibu.ostpc.dtos.user;


import ua.malibu.ostpc.dtos.BaseUuidDTO;
import ua.malibu.ostpc.enums.UserRole;
import ua.malibu.ostpc.models.User;

public class SimpleUserDTO extends BaseUuidDTO<User>{

    public SimpleUserDTO() {}

    private String name;

    private String surname;

    private UserRole role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public SimpleUserDTO convert(User object){
        this.setUuid(object.getUuid());
        this.setName(object.getName());
        this.setSurname(object.getSurname());
        this.setRole(object.getRole());
        return this;
    }

}
