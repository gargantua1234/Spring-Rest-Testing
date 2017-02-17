package com.aro;

import com.aro.controllers.UserControllerTest;
import com.aro.dao.UserDaoImplTest;
import com.aro.serivces.UserServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Arek on 17.02.2017.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserControllerTest.class,
        UserDaoImplTest.class,
        UserServiceImplTest.class
})
public class MainTest {
}
