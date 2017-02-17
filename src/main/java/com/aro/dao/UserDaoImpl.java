package com.aro.dao;

import com.aro.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Arek on 17.02.2017.
 */

@Repository
public class UserDaoImpl implements UserDao {

    private static final AtomicInteger index = new AtomicInteger();

    public static  List<User> users = new ArrayList<>(
            Arrays.asList(
                    new User(index.incrementAndGet(), "John", "Doe"),
                    new User(index.incrementAndGet(), "James", "May"),
                    new User(index.incrementAndGet(), "Richard", "Hammond"),
                    new User(index.incrementAndGet(), "The American", "Driver"),
                    new User(index.incrementAndGet(), "Jeremy", "Clarkson")
            ));




    @Override
    public List<User> listAllUsers() {
        return users;
    }

    @Override
    public User findById(int id) {
        User user;
        user = users.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .get()
        ;
        return user;
    }

    @Override
    public User findByName(String name) {
        return users.stream()
                .filter(x->x.getName().equals(name))
                .findFirst()
                .get();
    }

    @Override
    public void create(User user) {
        user.setId(index.incrementAndGet());
        users.add(user);
    }

    @Override
    public void update(User user) {
        int index = users.indexOf(findById(user.getId()));
        users.set(index, user);
    }

    @Override
    public void delete(int  id) {
        User user = findById(id);
        users.remove(user);
    }
}
