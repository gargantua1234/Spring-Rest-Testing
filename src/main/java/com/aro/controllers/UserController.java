package com.aro.controllers;

import com.aro.model.User;
import com.aro.serivces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by Arek on 16.02.2017.
 */
@RestController
class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable int id){
        User user =  userService.findUserById(id);
        if(user!=null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uri){

        if(userService.exists(user))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        userService.create(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user){
        User currentUser = userService.findUserById(id);
        if(currentUser == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        currentUser.setName(user.getName());
        currentUser.setLastName(user.getLastName());
        userService.update(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        User user = userService.findUserById(id);

        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
