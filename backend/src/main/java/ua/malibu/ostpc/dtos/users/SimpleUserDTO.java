package ua.malibu.ostpc.dtos.users;


import ua.malibu.ostpc.dtos.IDto;
import ua.malibu.ostpc.enums.UserRole;
import ua.malibu.ostpc.models.User;

public class SimpleUserDTO implements IDto<User>{

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
        this.setName(object.getName());
        this.setSurname(object.getSurname());
        this.setRole(object.getRole());
        return this;
    }

}
