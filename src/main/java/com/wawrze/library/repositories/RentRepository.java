package com.wawrze.library.repositories;

import com.wawrze.library.domains.rents.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RentRepository extends CrudRepository<Rent, Integer> {

    @Override
    List<Rent> findAll();

    Optional<Rent> findById(Integer id);

    @Override
    <S extends Rent> S save(S rent);

    @Override
    void delete(Integer id);

}