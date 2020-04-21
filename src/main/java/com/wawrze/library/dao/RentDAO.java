package com.wawrze.library.dao;

import com.wawrze.library.domains.rents.Rent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RentDAO extends CrudRepository<Rent, Integer> {

    @Override
    List<Rent> findAll();

    Optional<Rent> findById(Integer id);

    @Override
    <S extends Rent> S save(S rent);

    @Override
    void delete(Integer id);

    Optional<Rent> findByBook_Id(int bookId);

    @Query
    List<Rent> getRentsByUserId(@Param("userId") int userId);

}