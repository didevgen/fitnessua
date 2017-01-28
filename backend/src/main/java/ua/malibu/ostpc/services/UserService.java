package ua.malibu.ostpc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.malibu.ostpc.daos.UserDAO;
import ua.malibu.ostpc.models.users.User;

@Service
public class UserService {
    @Autowired
    private UserDAO userDao;

    public void saveUser(User user) {
        userDao.insert(user);
    }

    public User getUser(String uuid) {
        return userDao.get(uuid);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }
}
