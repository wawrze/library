package com.wawrzelibrary.library.controllers;

import com.wawrzelibrary.library.domains.users.UserDto;
import com.wawrzelibrary.library.exeptions.UserNotFoundException;
import com.wawrzelibrary.library.mappers.UserMapper;
import com.wawrzelibrary.library.services.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserDbService service;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getUsers")
    public List<UserDto> getUsers() {
        return userMapper.mapToUserDtoList(service.getAllUsers());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getUser")
    public UserDto getUser(@RequestParam Integer userId) throws UserNotFoundException {
        return userMapper.mapToUserDto(service.getUser(userId).orElseThrow(UserNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateUser")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userMapper.mapToUserDto(service.saveUser(userMapper.mapToUser(userDto)));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createUser",
            consumes = APPLICATION_JSON_VALUE
    )
    public void createUser(@RequestBody UserDto userDto) {
        service.saveUser(userMapper.mapToUser(userDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteUser")
    public void deleteUser(@RequestParam Integer userId) {
        service.deleteUser(userId);
    }

}