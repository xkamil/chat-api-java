package com.xkamil.controllers;


import com.xkamil.handlers.exceptions.ItemAlreadyExistsException;
import com.xkamil.handlers.exceptions.ItemNotFoundException;
import com.xkamil.repositories.UserRepository;
import com.xkamil.storage.Token;
import com.xkamil.storage.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "id") String id) throws Exception, ItemNotFoundException {

        User user = userRepository.findById(id);

        if (user == null) {
            throw new ItemNotFoundException("User with id " + id + " not found");
        }

        return user;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() throws Exception {

        return userRepository.findAll();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Token addUser(@RequestParam("username") String usernamem,
                         @RequestParam("password") String password) throws Exception {

        if (userRepository.findByName(usernamem) != null) {
            throw new ItemAlreadyExistsException(String.format("User %s already exists.", usernamem));
        }

        return userRepository.add(usernamem, password);
    }


}