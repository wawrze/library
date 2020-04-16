package com.wawrze.library.controllers;

import com.wawrze.library.domains.users.UserDto;
import com.wawrze.library.exeptions.UserNotFoundException;
import com.wawrze.library.mappers.UserMapper;
import com.wawrze.library.services.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.wawrze.library.filters.AuthFilter.IS_ADMIN_KEY;
import static com.wawrze.library.filters.AuthFilter.USER_ID_KEY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserDbService service;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserDbService service, UserMapper userMapper) {
        this.service = service;
        this.userMapper = userMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getUsers")
    public List<UserDto> getUsers(HttpServletRequest request) {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        return userMapper.mapToUserDtoList(service.getAllUsers(isAdmin));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getUser")
    public UserDto getUser(@RequestParam Integer userId, HttpServletRequest request) throws UserNotFoundException {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        int id = (int) request.getSession().getAttribute(USER_ID_KEY);
        return userMapper.mapToUserDto(service.getUser(userId, id, isAdmin).orElseThrow(UserNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateUser")
    public UserDto updateUser(@RequestBody UserDto userDto, HttpServletRequest request) {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        return userMapper.mapToUserDto(service.saveUser(userMapper.mapToUser(userDto), isAdmin));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createUser",
            consumes = APPLICATION_JSON_VALUE
    )
    public void createUser(@RequestBody UserDto userDto, HttpServletRequest request) {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        service.saveUser(userMapper.mapToUser(userDto), isAdmin);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteUser")
    public void deleteUser(@RequestParam Integer userId, HttpServletRequest request) {
        boolean isAdmin = (boolean) request.getSession().getAttribute(IS_ADMIN_KEY);
        service.deleteUser(userId, isAdmin);
    }

}