package com.wawrzelibrary.library.domains.users;

import com.wawrzelibrary.library.domains.rents.Rent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate accountCreationDate;
    private List<Rent> rentList;

}