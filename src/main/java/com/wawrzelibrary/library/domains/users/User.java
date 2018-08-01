package com.wawrzelibrary.library.domains.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")
public class User {

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountCreationDate = new Date(LocalDate.now().getYear() - 1900,
                LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "accountCreationDate")
    private Date accountCreationDate;

}