package com.wawrzelibrary.library.repositories;

import com.wawrzelibrary.library.domains.books.Book;
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
    Book save(Book book);

    @Override
    void delete(Integer id);

}