package com.wawrzelibrary.library.domains.titles;

import com.wawrzelibrary.library.domains.books.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="titles")
public class Title {

    public Title(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.bookList = new ArrayList<>();
    }

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

    @OneToMany(
            targetEntity = Book.class,
            mappedBy = "title",
            fetch = FetchType.EAGER
    )
    @Column(name = "bookList")
    private List<Book> bookList;

}