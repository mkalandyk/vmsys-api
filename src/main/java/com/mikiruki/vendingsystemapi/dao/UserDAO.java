package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.User;

import java.util.List;

public interface UserDAO {

    boolean save(User user);
    boolean update(User user);
    User findById(Integer id);
    User findByUsernameAndPassword(String username, String password);
    User findByUsernameOrEmail(String username, String email);
    List<User> list();

}
