package ru.mystudy.service;

import org.springframework.stereotype.Service;
import ru.mystudy.repository.UserDao;
import ru.mystudy.dto.User;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDAO) {
        this.userDao = userDAO;
    }

    public User findById(Long id) {
        if (id == null) {
            throw new NullPointerException("Parameter id is null");
        }
        return userDao.getById(id);
    }

    public List<User> findAll() {
        return userDao.getAll();
    }

    public User createUser(User user) {
        this.checkUser(user);
        return userDao.create(user);
    }

    public User updateUser(User user) {
        this.checkUser(user);
        return userDao.update(user);
    }

    public void deleteUser(User user) {
        this.checkUser(user);
        userDao.delete(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new NullPointerException("Parameter user is null");
        }
    }
}
