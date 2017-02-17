package com.aro.dao;

import com.aro.model.User;

import java.util.List;

/**
 * Created by Arek on 16.02.2017.
 */
public interface UserDao {
    List<User> listAllUsers();
    User findById(int id);
    User findByName(String name);
    void create(User user);
    void update(User user);
    void delete(int id);
}
