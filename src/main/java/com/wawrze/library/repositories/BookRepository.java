package com.wawrze.library.repositories;

import com.wawrze.library.domains.books.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    @Override
    List<Book> findAll();

    Optional<Book> findById(Integer id);

    @Override
    <S extends Book> S save(S book);

    @Override
    void delete(Integer id);

}