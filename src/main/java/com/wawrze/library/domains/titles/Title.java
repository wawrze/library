package com.wawrze.library.domains.titles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "titles")
@NamedNativeQuery(
        name = "Title.getTitlesWithAvailableBooks",
        query = "SELECT * FROM titles t WHERE t.id IN (SELECT book_title FROM books WHERE id NOT IN (SELECT book_id FROM rents))",
        resultClass = Title.class
)
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publicationYear")
    private int publicationYear;

}