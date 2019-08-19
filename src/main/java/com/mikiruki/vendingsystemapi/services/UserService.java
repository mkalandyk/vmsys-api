package com.mikiruki.vendingsystemapi.services;

import com.mikiruki.vendingsystemapi.dao.UserDAO;
import com.mikiruki.vendingsystemapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    User getAdmin() {
        return this.userDAO.findByUsernameOrEmail("admin","");
    }

    public User login(User sentUsr) {
        if (sentUsr != null && !sentUsr.getUsername().isEmpty() && !sentUsr.getPassword().isEmpty()) {
            User user = userDAO.findByUsernameAndPassword(sentUsr.getUsername(), sentUsr.getPassword());
            if (user != null && user.getUsername().equals(sentUsr.getUsername()) && user.getPassword().equals(sentUsr.getPassword())) {
                return user;
            }
        }
        return null;
    }

}
