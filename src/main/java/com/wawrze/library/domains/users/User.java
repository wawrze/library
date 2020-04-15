package com.wawrze.library.domains.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "accountCreationDate")
    private Date accountCreationDate;

    public User(Integer id, String login, String password, String firstName, String lastName, Date accountCreationDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        if (accountCreationDate == null) {
            this.accountCreationDate = new Date(System.currentTimeMillis());
        } else {
            this.accountCreationDate = accountCreationDate;
        }
    }

}