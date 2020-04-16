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
    @Column(name = "rentDays")
    private Integer rentDays;

    public Book(Integer id, Title title, Integer rentDays) {
        this.id = id;
        this.title = title;
        this.rentDays = rentDays;
    }

}