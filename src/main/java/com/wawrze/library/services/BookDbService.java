package com.wawrze.library.services;

import com.wawrze.library.dao.BookDAO;
import com.wawrze.library.domains.books.Book;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookDbService {

    private final BookDAO bookDAO;

    @Autowired
    public BookDbService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    public Optional<Book> getBook(final Integer id) {
        return bookDAO.findById(id);
    }

    public Book saveBook(final Book book, final UserRole userRole) {
        if (userRole != UserRole.ADMIN) throw new ForbiddenException("Only administrator can manage books!");
        return bookDAO.save(book);
    }

    public void deleteBook(final Integer id, final UserRole userRole) {
        if (userRole != UserRole.ADMIN) throw new ForbiddenException("Only administrator can manage books!");
        bookDAO.delete(id);
    }

}