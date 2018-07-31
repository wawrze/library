package com.wawrzelibrary.library.repositories;

import com.wawrzelibrary.library.domains.titles.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TitleRepository extends CrudRepository<Title, Integer> {

    @Override
    List<Title> findAll();

    Optional<Title> findById(Integer id);

    @Override
    Title save(Title title);

    @Override
    void delete(Integer id);

}