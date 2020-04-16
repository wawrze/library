package com.wawrze.library.services;

import com.wawrze.library.dao.BookDAO;
import com.wawrze.library.dao.RentDAO;
import com.wawrze.library.dao.UserDAO;
import com.wawrze.library.domains.books.Book;
import com.wawrze.library.domains.rents.Rent;
import com.wawrze.library.domains.users.User;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.BookNotFoundException;
import com.wawrze.library.exeptions.ForbiddenException;
import com.wawrze.library.exeptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RentDbService {

    private final RentDAO rentDAO;
    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    @Autowired
    public RentDbService(RentDAO rentDAO, BookDAO bookDAO, UserDAO userDAO) {
        this.rentDAO = rentDAO;
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    public List<Rent> getAllRents(final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage rents!");
        return rentDAO.findAll();
    }

    public List<Rent> getUserRents(final int userId, final int id, final UserRole userRole) {
        if (userRole == UserRole.USER && userId != id)
            throw new ForbiddenException("Only librarian can manage others rents!");
        return rentDAO.getRentsByUserId(userId);
    }

    public Optional<Rent> getRent(final Integer id, final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage rents!");
        return rentDAO.findById(id);
    }

    public Rent updateRent(final Rent rent, final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage rents!");
        return rentDAO.save(rent);
    }

    public void createRent(final Integer userId, final Integer bookId, final UserRole userRole) throws BookNotFoundException, UserNotFoundException {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage rents!");

        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookDAO.findById(bookId).orElseThrow(BookNotFoundException::new);
        if (book.getRent() != null) throw new ForbiddenException("Book already rented!");

        Date now = new Date(System.currentTimeMillis());
        Date finishDate = new Date(System.currentTimeMillis() + 24 * 3600 * 1000 * book.getRentDays());
        Rent rent = new Rent(null, user, book, now, finishDate);

        rentDAO.save(rent);
    }

    public void deleteRent(final Integer id, final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage rents!");
        rentDAO.delete(id);
    }

}