package com.wawrzelibrary.library.domains.rents;

import com.wawrzelibrary.library.domains.users.User;
import com.wawrzelibrary.library.domains.books.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="rents")
public class Rent {

    public Rent(User user, Book book, Date rentFinishDate) {
        this.user = user;
        this.book = book;
        this.rentStartDate = new Date(LocalDate.now().getYear() - 1900,
                LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());
        this.rentFinishDate = rentFinishDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @Column(name = "rentStartedDate")
    private Date rentStartDate;

    @Column(name = "rentFinishDate")
    private Date rentFinishDate;

}