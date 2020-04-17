package com.wawrze.library.domains.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Date accountCreationDate;
    private UserRole userRole;
    private String token;

    public void setToken(String token) {
        this.token = token;
    }
}