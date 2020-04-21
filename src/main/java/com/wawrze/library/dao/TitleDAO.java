package com.wawrze.library.dao;

import com.wawrze.library.domains.titles.Title;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TitleDAO extends CrudRepository<Title, Integer> {

    @Override
    List<Title> findAll();

    Optional<Title> findById(Integer id);

    @Override
    <S extends Title> S save(S entity);

    @Override
    void delete(Integer id);

    @Query
    List<Title> getTitlesWithAvailableBooks();

    @Override
    void deleteAll();
}