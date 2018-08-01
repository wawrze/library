package com.wawrzelibrary.library.domains.rents;

import com.wawrzelibrary.library.domains.users.User;
import com.wawrzelibrary.library.domains.books.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {

    private Integer id;
    private User user;
    private Book book;
    private Date rentStartDate;
    private Date rentFinishDate;

}