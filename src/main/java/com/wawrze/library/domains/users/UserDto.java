package com.wawrze.library.domains.users;

import com.wawrze.library.domains.rents.RentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

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
    private Set<RentDto> rents;

    public void setToken(String token) {
        this.token = token;
    }
}