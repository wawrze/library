package com.wawrzelibrary.library.repositories;

import com.wawrzelibrary.library.domains.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    List<User> findAll();

    Optional<User> findById(Integer id);

    @Override
    User save(User user);

    @Override
    void delete(Integer id);

    User findByLogin(String login);

}