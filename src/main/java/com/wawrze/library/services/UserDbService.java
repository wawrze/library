package com.wawrze.library.services;

import com.wawrze.library.dao.UserDAO;
import com.wawrze.library.domains.users.Credentials;
import com.wawrze.library.domains.users.User;
import com.wawrze.library.domains.users.UserDto;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.ForbiddenException;
import com.wawrze.library.exeptions.UserNotFoundException;
import com.wawrze.library.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wawrze.library.filters.AuthFilter.USER_ID_KEY;

@Service
@Transactional
public class UserDbService {

    private final UserDAO userDAO;
    private final UserMapper userMapper;

    @Autowired
    public UserDbService(UserDAO userDAO, UserMapper userMapper, DataBaseFillerService dbFillerService) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
        if (userDAO.count() == 0) dbFillerService.fillDb();
    }

    public List<User> getUsers(final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage users!");
        return userDAO.findAllByUserRoleEquals(UserRole.USER);
    }

    public List<User> getLibrarians(final UserRole userRole) {
        if (userRole != UserRole.ADMIN) throw new ForbiddenException("Only admin can manage librarians!");
        return userDAO.findAllByUserRoleEquals(UserRole.LIBRARIAN);
    }

    public List<User> getAdmins(final UserRole userRole) {
        if (userRole != UserRole.ADMIN) throw new ForbiddenException("Only admin can manage other admins!");
        return userDAO.findAllByUserRoleEquals(UserRole.ADMIN);
    }

    public Optional<User> getUser(final Integer id, final int userId, final UserRole userRole) {
        if (id != userId && userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage users!");
        return userDAO.findById(id);
    }

    public User saveUser(final User user, final UserRole userRole, final int userId) {
        if (user.getId() != null && userId != user.getId() && userRole == UserRole.USER)
            throw new ForbiddenException("Only librarian can manage users!");
        try {
            User userInDb = userDAO.findById(user.getId()).orElseThrow(UserNotFoundException::new);
            user.setToken(userInDb.getToken());
            if (user.getPassword() == null || user.getPassword().isEmpty()) user.setPassword(userInDb.getPassword());
        } catch (UserNotFoundException e) {
            User userInDb = userDAO.findByLogin(user.getLogin());
            if (userInDb != null) throw new ForbiddenException("Login occupied!");
            user.setAccountCreationDate(new Date(System.currentTimeMillis()));
        }
        return userDAO.save(user);
    }

    public void deleteUser(final Integer id, final UserRole userRole) {
        if (userRole == UserRole.USER) throw new ForbiddenException("Only librarian can manage users!");
        userDAO.delete(id);
    }

    public UserDto login(final Credentials credentials) {
        User user = userDAO.findByLogin(credentials.getLogin());

        if (user == null) throw new ForbiddenException("No user!");
        if (!user.getPassword().equals(credentials.getPassword())) throw new ForbiddenException("Wrong password!");

        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userDAO.save(user);

        UserDto userDto = userMapper.mapToUserDto(user);
        userDto.setToken(token);

        return userDto;
    }

    public void logout(HttpServletRequest request) {
        int userId = (int) request.getSession().getAttribute(USER_ID_KEY);
        User user = userDAO.findById(userId).orElseThrow(null);
        user.setToken(null);
        userDAO.save(user);
        request.getSession().invalidate();
    }

}