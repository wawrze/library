package com.wawrzelibrary.library.services;

import com.wawrzelibrary.library.domains.books.Book;
import com.wawrzelibrary.library.domains.rents.Rent;
import com.wawrzelibrary.library.domains.titles.Title;
import com.wawrzelibrary.library.domains.users.User;
import com.wawrzelibrary.library.repositories.BookRepository;
import com.wawrzelibrary.library.repositories.RentRepository;
import com.wawrzelibrary.library.repositories.TitleRepository;
import com.wawrzelibrary.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DbService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Book> getBook(final Integer id) {
        return bookRepository.findById(id);
    }

    public Optional<Rent> getRent(final Integer id) {
        return rentRepository.findById(id);
    }

    public Optional<Title> getTitle(final Integer id) {
        return titleRepository.findById(id);
    }

    public Optional<User> getUser(final Integer id) {
        return userRepository.findById(id);
    }

    public Book saveBook(final Book book) {
        return bookRepository.save(book);
    }

    public Rent saveRent(final Rent rent) {
        return rentRepository.save(rent);
    }

    public Title saveTitle(final Title title) {
        return titleRepository.save(title);
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public void deleteTitle(final Integer id) {
        titleRepository.delete(id);
    }

    public void deleteUser(final Integer id) {
        userRepository.delete(id);
    }

    public void deleteRent(final Integer id) {
        rentRepository.delete(id);
    }

    public void deleteBook(final Integer id) {
        bookRepository.delete(id);
    }

}