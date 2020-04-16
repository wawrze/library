package com.wawrze.library.domains.rents;

import com.wawrze.library.domains.books.Book;
import com.wawrze.library.domains.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rents")
@NamedNativeQuery(
        name = "Rent.getRentsByUserId",
        query = "SELECT * FROM rents WHERE user_id = :userId",
        resultClass = Rent.class
)
public class Rent {

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

    public void setUser(User user) {
        this.user = user;
    }
}