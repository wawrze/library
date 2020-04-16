package com.wawrze.library.services;

import com.wawrze.library.domains.users.Credentials;
import com.wawrze.library.domains.users.User;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.ForbiddenException;
import com.wawrze.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wawrze.library.filters.AuthFilter.*;

@Service
@Transactional
public class UserDbService {

    private final UserRepository userRepository;

    @Autowired
    public UserDbService(UserRepository userRepository) {
        this.userRepository = userRepository;
        createAdminIfNeeded();
    }

    public List<User> getAllUsers(final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage users!");
        return userRepository.findAll();
    }

    public Optional<User> getUser(final Integer id, final int userId, final boolean isAdmin) {
        if (id != userId && !isAdmin) throw new ForbiddenException("Only administrator can manage users!");
        return userRepository.findById(id);
    }

    public User saveUser(final User user, final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage users!");
        return userRepository.save(user);
    }

    public void deleteUser(final Integer id, final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage users!");
        userRepository.delete(id);
    }

    public String login(final Credentials credentials, HttpServletRequest request) {
        User user = userRepository.findByLogin(credentials.getLogin());

        if (user == null) throw new ForbiddenException("No user!");
        if (!user.getPassword().equals(credentials.getPassword())) throw new ForbiddenException("Wrong password!");

        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute(USER_ID_KEY, user.getId());
        request.getSession().setAttribute(TOKEN_KEY, token);
        request.getSession().setAttribute(IS_ADMIN_KEY, user.getUserRole() == UserRole.ADMIN);

        return token;
    }

    private void createAdminIfNeeded() {
        if (userRepository.countAllByUserRoleEquals(UserRole.ADMIN) == 0) {
            User admin = new User(
                    null,
                    "admin",
                    "admin",
                    "Admin",
                    "Admin",
                    null,
                    UserRole.ADMIN
            );
            userRepository.save(admin);
        }
    }

}