package com.aro.serivces;

import com.aro.dao.UserDao;
import com.aro.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Arek on 17.02.2017.
 */
public class UserServiceImplTest {

    private static List<User> users;


    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void init(){
        initMocks(this);

        users = new ArrayList<>(
                Arrays.asList(
                        new User (1, "Homer", "Simpson"),
                        new User (2, "Bart", "Simpson"),
                        new User (3, "Marge", "Simpson"),
                        new User (4, "Lisa", "Simpson"),
                        new User (5, "Maggie", "Simpson")
                )
        );
    }


    @Test
    public void test_get_all_users(){
        when(userDao.listAllUsers()).thenReturn(users);
        List<User> allUsers = userService.getAllUsers();
        assertThat(allUsers, hasSize(5));
    }
}