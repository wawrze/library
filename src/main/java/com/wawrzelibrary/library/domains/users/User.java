package com.wawrzelibrary.library.domains.users;

import com.wawrzelibrary.library.domains.rents.Rent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")
public class User {

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountCreationDate = LocalDate.now();
        this.rentList = new ArrayList<>();
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
    private LocalDate accountCreationDate;

    @OneToMany(
            targetEntity = Rent.class,
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    @Column(name = "rentList")
    private List<Rent> rentList;

}