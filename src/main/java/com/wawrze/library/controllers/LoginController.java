package com.wawrze.library.controllers;

import com.wawrze.library.domains.users.LoginDto;
import com.wawrze.library.services.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

    private UserDbService service;

    @Autowired
    public LoginController(UserDbService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        return service.login(loginDto, request);
    }

}