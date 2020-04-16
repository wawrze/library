package com.wawrze.library.repositories;

import com.wawrze.library.domains.users.User;
import com.wawrze.library.domains.users.UserRole;
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
    <S extends User> S save(S entity);

    @Override
    void delete(Integer id);

    User findByLogin(String login);

    Integer countAllByUserRoleEquals(UserRole userRole);

}