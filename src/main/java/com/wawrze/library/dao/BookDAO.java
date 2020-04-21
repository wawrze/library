package com.wawrze.library.dao;

import com.wawrze.library.domains.books.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BookDAO extends CrudRepository<Book, Integer> {

    @Override
    List<Book> findAll();

    Optional<Book> findById(Integer id);

    @Override
    <S extends Book> S save(S book);

    @Override
    void delete(Integer id);

    @Query
    List<Book> getBooksByTitleId(@Param("titleId") int titleId);

    @Override
    void deleteAll();
}