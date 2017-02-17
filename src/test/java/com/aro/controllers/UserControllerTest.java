package com.aro.controllers;

import com.aro.model.User;
import com.aro.serivces.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Arek on 17.02.2017.
 */

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static String objectAsString(final User user){
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User(1, "John", "Doe"),
                new User(2, "Homer", "Simpson"),
                new User(3, "Bart", "Simpson")
        );

        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Homer")))
                .andExpect(jsonPath("$[1].lastName", is("Simpson")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Bart")))
                .andExpect(jsonPath("$[2].lastName", is("Simpson")))
        ;

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);

    }

    @Test
    public void testGetByIdSuccess() throws Exception {
        User user = new User(1, "John", "Doe");
        when(userService.findUserById(1)).thenReturn(user);

        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
        ;

        verify(userService, times(1)).findUserById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void user_get_by_id_fail_404_not_found() throws Exception {
        when(userService.findUserById(1)).thenReturn(null);
        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isNotFound())
        ;

        verify(userService, times(1)).findUserById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_create_user_success() throws Exception {
        User user = new User( "Homer", "Simpson");
        when(userService.exists(user)).thenReturn(false);
        doNothing().when(userService).create(user);

        mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost/user")))
        ;

        verify(userService, times(1)).exists(user);
        verify(userService, times(1)).create(user);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_create_user_failed_conflict_409_conflict() throws Exception {
        User user = new User("Homer", "Simpson");
        when(userService.exists(user)).thenReturn(true);

        mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectAsString(user)))
                .andExpect(status().isConflict())
        ;

        verify(userService, times(1)).exists(user);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_update_user_success() throws Exception {
        User user = new User(1, "Homer", "Simpson");
        when(userService.findUserById(user.getId())).thenReturn(user);
        doNothing().when(userService).update(user);

        mockMvc.perform(
                put("/user/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectAsString(user)))
                .andExpect(status().isOk())
        ;

        verify(userService, times(1)).findUserById(user.getId());
        verify(userService, times(1)).update(user);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_update_user_failed_404_not_found() throws Exception {
        User user = new User(1, "User", "Doesn't exist");
        when(userService.findUserById(user.getId())).thenReturn(null);

        mockMvc.perform(
                put("/user/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectAsString(user)))
                .andExpect(status().isNotFound())
        ;

        verify(userService, times(1)).findUserById(user.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_delete_user_success() throws  Exception {
       User user = new User(1, "Homer", "Simpson");
        when(userService.findUserById(user.getId())).thenReturn(user);
        doNothing().when(userService).delete(user.getId());

        mockMvc.perform(
                delete("/user/{id}", user.getId()))
                .andExpect(status().isOk())
        ;

        verify(userService, times(1)).findUserById(user.getId());
        verify(userService, times(1)).delete(user.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_delete_user_failed_404_not_found() throws  Exception {
        User user = new User(1, "Homer", "Simpson");
        when(userService.findUserById(user.getId())).thenReturn(null);


        mockMvc.perform(
                delete("/user/{id}", user.getId()))
                .andExpect(status().isNotFound())
        ;

        verify(userService, times(1)).findUserById(user.getId());
        verifyNoMoreInteractions(userService);
    }





}