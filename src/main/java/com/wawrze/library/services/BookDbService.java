package com.wawrze.library.services;

import com.wawrze.library.domains.books.Book;
import com.wawrze.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookDbService {

    private final BookRepository bookRepository;

    @Autowired
    public BookDbService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBook(final Integer id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(final Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(final Integer id) {
        bookRepository.delete(id);
    }

}