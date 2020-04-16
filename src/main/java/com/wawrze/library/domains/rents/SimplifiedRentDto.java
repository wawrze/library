package com.wawrze.library.domains.rents;

import com.wawrze.library.domains.books.BookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimplifiedRentDto {

    private Integer id;
    private Integer user;
    private BookDto book;
    private Date rentStartDate;
    private Date rentFinishDate;

}