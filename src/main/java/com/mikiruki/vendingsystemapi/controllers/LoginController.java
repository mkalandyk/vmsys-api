package com.mikiruki.vendingsystemapi.controllers;

import com.mikiruki.vendingsystemapi.models.User;
import com.mikiruki.vendingsystemapi.services.MailingService;
import com.mikiruki.vendingsystemapi.services.UserService;
import com.mikiruki.vendingsystemapi.utils.JSONParserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private JSONParserHelper<User> userParserHelper;

    @GetMapping("/mailtest")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public void mailTest() {
        mailingService.sendTestMessage();
    }

    @PostMapping(path = "", consumes = "application/json")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public User login(@RequestBody String json) {
        User sentUsr = userParserHelper.parseJSONToObject(json);
        return this.userService.login(sentUsr);
    }

}
