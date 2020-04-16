package com.wawrze.library.domains.books;

import com.wawrze.library.domains.titles.TitleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Integer id;
    private TitleDto title;
    private Date rentTo;

}
