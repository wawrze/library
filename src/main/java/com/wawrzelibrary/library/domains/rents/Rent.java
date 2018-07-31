package com.wawrzelibrary.library.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="rents")
public class Rent {

    public Rent(User user, Book book, LocalDate rentFinishDate) {
        this.user = user;
//        this.book = book;
        this.rentStartDate = LocalDate.now();
        this.rentFinishDate = rentFinishDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId")
    private Book book;

    @Column(name = "rentStartedDate")
    private LocalDate rentStartDate;

    @Column(name = "rentFinishDate")
    private LocalDate rentFinishDate;

}