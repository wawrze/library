package com.wawrze.library.dao;

import com.wawrze.library.domains.users.User;
import com.wawrze.library.domains.users.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserDAO extends CrudRepository<User, Integer> {

    Optional<User> findById(Integer id);

    @Override
    <S extends User> S save(S entity);

    @Override
    void delete(Integer id);

    User findByLogin(String login);

    User findByToken(String token);

    Integer countAllByUserRoleEquals(UserRole userRole);

    List<User> findAllByUserRoleEquals(UserRole userRole);

}