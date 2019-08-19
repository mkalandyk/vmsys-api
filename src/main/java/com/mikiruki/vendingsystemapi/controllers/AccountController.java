package com.mikiruki.vendingsystemapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikiruki.vendingsystemapi.dao.UserDAO;
import com.mikiruki.vendingsystemapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping(path = "/update", consumes = "application/json")
    public boolean update(@RequestBody String json) throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        User user = objMapper.readValue(json, User.class);
        return userDAO.update(user);
    }

    @PostMapping(path = "/create", consumes = "application/json")
    public boolean create(@RequestBody String json) throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        User user = objMapper.readValue(json, User.class);
        if(userDAO.findByUsernameOrEmail(user.getUsername(), user.getEmail()) != null)
            return false;
        else {
            user.setRole("user");
            return userDAO.save(user);
        }
    }
}
