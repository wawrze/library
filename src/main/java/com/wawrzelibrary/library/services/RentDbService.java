package com.wawrzelibrary.library.services;

import com.wawrzelibrary.library.domains.rents.Rent;
import com.wawrzelibrary.library.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RentDbService {

    @Autowired
    private RentRepository rentRepository;

    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    public Optional<Rent> getRent(final Integer id) {
        return rentRepository.findById(id);
    }

    public Rent saveRent(final Rent rent) {
        return rentRepository.save(rent);
    }

    public void deleteRent(final Integer id) {
        rentRepository.delete(id);
    }

}