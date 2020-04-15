package com.wawrze.library.services;

import com.wawrze.library.domains.users.LoginDto;
import com.wawrze.library.domains.users.User;
import com.wawrze.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wawrze.library.filters.AuthFilter.TOKEN_KEY;
import static com.wawrze.library.filters.AuthFilter.USER_KEY;

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

    public String login(final LoginDto loginDto, HttpServletRequest request) {
        User user = userRepository.findByLogin(loginDto.getLogin());

        if (user == null) return "no user";
        if (!user.getPassword().equals(loginDto.getPassword())) return "wrong password";

        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute(USER_KEY, user);      // FIXME: serialization problem, maybe replace with just user id?
        request.getSession().setAttribute(TOKEN_KEY, token);

        return token;
    }

}