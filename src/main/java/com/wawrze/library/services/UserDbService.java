package com.wawrze.library.services;

import com.wawrze.library.dao.UserDAO;
import com.wawrze.library.domains.users.Credentials;
import com.wawrze.library.domains.users.User;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.ForbiddenException;
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

    private final UserDAO userDAO;

    @Autowired
    public UserDbService(UserDAO userDAO) {
        this.userDAO = userDAO;
        createAdminIfNeeded();
    }

    public List<User> getAllUsers(final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage users!");
        return userDAO.findAll();
    }

    public Optional<User> getUser(final Integer id, final int userId, final UserRole userRole) {
        if (id != userId && userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage users!");
        return userDAO.findById(id);
    }

    public User saveUser(final User user, final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage users!");
        return userDAO.save(user);
    }

    public void deleteUser(final Integer id, final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage users!");
        userDAO.delete(id);
    }

    public String login(final Credentials credentials, HttpServletRequest request) {
        User user = userDAO.findByLogin(credentials.getLogin());

        if (user == null) throw new ForbiddenException("No user!");
        if (!user.getPassword().equals(credentials.getPassword())) throw new ForbiddenException("Wrong password!");

        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute(USER_ID_KEY, user.getId());
        request.getSession().setAttribute(TOKEN_KEY, token);
        request.getSession().setAttribute(USER_ROLE_KEY, user.getUserRole());

        return token;
    }

    private void createAdminIfNeeded() {
        if (userDAO.countAllByUserRoleEquals(UserRole.ADMIN) == 0) {
            User admin = new User(
                    null,
                    "admin",
                    "admin",
                    "Admin",
                    "Admin",
                    null,
                    UserRole.ADMIN
            );
            userDAO.save(admin);
        }
    }

}