package com.wawrze.library.controllers;

import com.wawrze.library.domains.users.Credentials;
import com.wawrze.library.services.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class LoginController {

    private UserDbService service;

    @Autowired
    public LoginController(UserDbService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, value = "login")
    public String login(@RequestBody Credentials credentials, HttpServletRequest request) {
        return service.login(credentials, request);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

}