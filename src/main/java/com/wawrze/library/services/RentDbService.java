package com.wawrze.library.services;

import com.wawrze.library.domains.rents.Rent;
import com.wawrze.library.exeptions.ForbiddenException;
import com.wawrze.library.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RentDbService {

    private final RentRepository rentRepository;

    @Autowired
    public RentDbService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public List<Rent> getAllRents(final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage rents!");
        return rentRepository.findAll();
    }

    public Optional<Rent> getRent(final Integer id, final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage rents!");
        return rentRepository.findById(id);
    }

    public Rent saveRent(final Rent rent, final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage rents!");
        return rentRepository.save(rent);
    }

    public void deleteRent(final Integer id, final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage rents!");
        rentRepository.delete(id);
    }

}