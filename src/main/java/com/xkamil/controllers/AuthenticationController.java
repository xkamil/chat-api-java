package com.xkamil.controllers;


import com.xkamil.handlers.exceptions.ItemAlreadyExistsException;
import com.xkamil.handlers.exceptions.ItemNotFoundException;
import com.xkamil.repositories.TokenRepository;
import com.xkamil.repositories.UserRepository;
import com.xkamil.storage.Token;
import com.xkamil.storage.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Token login(@RequestParam("username") String username,
                       @RequestParam("password") String password) throws Exception, ItemNotFoundException {

        User user = userRepository.findByName(username);

        if (user == null) {
            throw new ItemNotFoundException("User not found");
        }

        if (!user.isPasswordMatch(password)) {
            throw new ItemNotFoundException("User password not match");
        }

        return tokenRepository.addToken(user.getId());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Token register(@RequestParam("username") String username,
                          @RequestParam("password") String password
    ) throws Exception, ItemNotFoundException, ItemAlreadyExistsException {

        if (userRepository.findByName(username) != null) {
            throw new ItemAlreadyExistsException("User " + username + " already exists");
        }

        return userRepository.add(username, password);
    }


}