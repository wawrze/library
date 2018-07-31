package com.wawrzelibrary.library.domains.titles;

import com.wawrzelibrary.library.domains.books.Book;
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
    private int publicationDate;
    private List<Book> bookList;

}