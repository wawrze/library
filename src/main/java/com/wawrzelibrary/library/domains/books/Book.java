package com.wawrzelibrary.library.domains.books;

import com.wawrzelibrary.library.domains.titles.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="books")
public class Book {

    public Book(Title title, String status) {
        this.title = title;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bookTitle")
    private Title title;

    @Column(name = "bookStatus")
    private String status;

}