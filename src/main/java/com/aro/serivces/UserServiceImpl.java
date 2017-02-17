package com.aro.serivces;

import com.aro.dao.UserDao;
import com.aro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Arek on 17.02.2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.listAllUsers();
    }

    @Override
    public User findUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public boolean exists(User user) {
        return userDao.findByName(user.getName()) != null;
    }

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }
}
