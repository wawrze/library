package com.wawrze.library.domains.books;

import com.wawrze.library.domains.rents.Rent;
import com.wawrze.library.domains.titles.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "bookTitle")
    private Title title;
    @OneToOne
    @JoinColumn(name = "rentId")
    private Rent rent;

    public Book(Integer id, Title title) {
        this.id = id;
        this.title = title;
    }

}