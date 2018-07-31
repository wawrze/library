package com.wawrzelibrary.library.domains.books;

import com.wawrzelibrary.library.domains.titles.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Integer id;
    private Title title;
    private String status;

}
