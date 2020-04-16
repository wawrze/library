package com.wawrze.library.domains.rents;

import com.wawrze.library.domains.books.BookDto;
import com.wawrze.library.domains.users.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {

    private Integer id;
    private UserDto user;
    private BookDto book;
    private Date rentStartDate;
    private Date rentFinishDate;

}