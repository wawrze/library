package com.wawrzelibrary.library.domains.titles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {

    private Integer id;
    private String title;
    private String author;
    private int publicationYear;

}