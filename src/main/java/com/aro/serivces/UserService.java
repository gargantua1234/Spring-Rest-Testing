package com.aro.serivces;

import com.aro.model.User;

import java.util.List;

/**
 * Created by Arek on 17.02.2017.
 */
public interface UserService {
    List<User> getAllUsers();
    User findUserById(int id);
    boolean exists(User user);
    void create(User user);
    void update(User user);
    void delete(int id);
}
