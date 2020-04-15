package com.wawrzelibrary.library.services;

import com.wawrzelibrary.library.domains.users.LoginDto;
import com.wawrzelibrary.library.domains.users.User;
import com.wawrzelibrary.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserDbService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(final Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public void deleteUser(final Integer id) {
        userRepository.delete(id);
    }

    public String login(final LoginDto loginDto) {
        User user = userRepository.findByLogin(loginDto.getLogin());
        if (user == null) return "no user";
        else if (!user.getPassword().equals(loginDto.getPassword())) return "wrong password";
        else return UUID.randomUUID().toString();
    }

}