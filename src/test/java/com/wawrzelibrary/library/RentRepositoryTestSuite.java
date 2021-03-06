package com.wawrzelibrary.library;

import com.wawrzelibrary.library.domains.books.Book;
import com.wawrzelibrary.library.domains.rents.Rent;
import com.wawrzelibrary.library.domains.titles.Title;
import com.wawrzelibrary.library.domains.users.User;
import com.wawrzelibrary.library.exeptions.RentNotFoundException;
import com.wawrzelibrary.library.repositories.BookRepository;
import com.wawrzelibrary.library.repositories.RentRepository;
import com.wawrzelibrary.library.repositories.TitleRepository;
import com.wawrzelibrary.library.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Profile("test")
public class RentRepositoryTestSuite {

    @Autowired
    RentRepository rentRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TitleRepository titleRepository;

    @Test
    public void testRentFindAll() {
        //Given
        Title title = new Title("Some title", "John Smith", 2000);
        User user1 = new User("Paul", "Jones");
        User user2 = new User("Patrick", "Watson");
        Book book1;
        Book book2;
        Rent rent1;
        Rent rent2;
        int bookId1;
        int bookId2;
        int titleId;
        int userId1;
        int userId2;
        int rentId1;
        int rentId2;
        List<Rent> resultList;
        //When
        titleRepository.save(title);
        titleId = title.getId();
        userRepository.save(user1);
        userRepository.save(user2);
        userId1 = user1.getId();
        userId2 = user2.getId();
        book1 = new Book(title, "Some_Status");
        book2 = new Book(title, "Other_Status");
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookId1 = book1.getId();
        bookId2 = book2.getId();
        rent1 = new Rent(user1, book1, new Date(LocalDate.now().getYear() - 1900,
                LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth() + 10));
        rent2 = new Rent(user2, book2, new Date(LocalDate.now().getYear() - 1900,
                LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth() + 10));
        rentRepository.save(rent1);
        rentRepository.save(rent2);
        rentId1 = rent1.getId();
        rentId2 = rent2.getId();
        resultList = rentRepository.findAll();
        //Then
        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void testFindById() {
        //Given
        Title title = new Title("Some Title", "John Smith", 2000);
        User user = new User("Paul", "Jones");
        Book book;
        Rent rent;
        Rent resultRent1 = null;
        Rent resultRent2;
        boolean resultNoRent = false;
        int titleId;
        int userId;
        int bookId;
        int rentId;
        Date rentFinishDate = new Date(LocalDate.now().getYear() - 1900,
                LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth() + 10);
        //When
        titleRepository.save(title);
        titleId = title.getId();
        book = new Book(title, "some status");
        bookRepository.save(book);
        bookId = book.getId();
        userRepository.save(user);
        userId = user.getId();
        rent = new Rent(user, book, rentFinishDate);
        rentRepository.save(rent);
        rentId = rent.getId();
        try {
            resultRent1 = rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
        }
        catch(RentNotFoundException e) {}
        try {
            resultRent2 = rentRepository.findById(0).orElseThrow(RentNotFoundException::new);
        }
        catch (RentNotFoundException e) {
            resultNoRent = true;
        }
        //Then
        Assert.assertEquals(resultRent1.getId(), rent.getId());
        Assert.assertEquals(resultRent1.getUser().getId(), rent.getUser().getId());
        Assert.assertEquals(resultRent1.getBook().getId(), rent.getBook().getId());
        Assert.assertEquals(resultRent1.getRentFinishDate(), rentFinishDate);
        Assert.assertTrue(resultNoRent);
    }

}