package com.wawrze.library.domains.titles;

import com.wawrze.library.domains.books.BookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {

    private Integer id;
    private String title;
    private String author;
    private int publicationYear;
    private List<BookDto> books;

}