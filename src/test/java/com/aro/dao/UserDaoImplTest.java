package com.aro.dao;

import com.aro.configuration.AppConfig;
import com.aro.configuration.WebInit;
import com.aro.model.User;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by Arek on 17.02.2017.
 */

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Before
    public void init(){
        userDao = new UserDaoImpl();
    }

    @Test
    public void test2_delete_user(){
        userDao.delete(2);
        assertThat(UserDaoImpl.users, not(hasItem(new User(2, "James", "May"))));
    }

    @Test
    public void test1_list_all_users(){
        List<User> userList = userDao.listAllUsers();
        assertThat(userList, is(UserDaoImpl.users));
        assertThat(userList, hasSize(5));
        assertThat(userList, hasItem(new User(2, "James", "May")));
    }

}