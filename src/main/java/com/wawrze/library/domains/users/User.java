package com.wawrze.library.domains.users;

import com.wawrze.library.domains.rents.Rent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "accountCreationDate", nullable = false)
    private Date accountCreationDate;
    @Column(name = "userRole", nullable = false)
    private UserRole userRole;
    @Column(name = "token")
    private String token;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @OrderColumn(name = "user_index")
    private Set<Rent> rents;

    public User(Integer id, String login, String password, String firstName, String lastName, Date accountCreationDate, UserRole userRole) {
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
        this.userRole = userRole;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }
}