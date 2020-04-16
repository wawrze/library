package com.wawrze.library.services;

import com.wawrze.library.dao.RentDAO;
import com.wawrze.library.domains.rents.Rent;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RentDbService {

    private final RentDAO rentDAO;

    @Autowired
    public RentDbService(RentDAO rentDAO) {
        this.rentDAO = rentDAO;
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

    public Rent saveRent(final Rent rent, final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage rents!");
        return rentDAO.save(rent);
    }

    public void deleteRent(final Integer id, final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage rents!");
        rentDAO.delete(id);
    }

}