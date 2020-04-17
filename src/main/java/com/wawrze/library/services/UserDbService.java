package com.wawrze.library.services;

import com.wawrze.library.dao.UserDAO;
import com.wawrze.library.domains.users.Credentials;
import com.wawrze.library.domains.users.User;
import com.wawrze.library.domains.users.UserDto;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.ForbiddenException;
import com.wawrze.library.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
    public UserDbService(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
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

    public User saveUser(final User user, final UserRole userRole, final int userId) {
        if (userId != user.getId() && userRole == UserRole.USER)
            throw new ForbiddenException("Only librarian can manage users!");
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

    private void createAdminIfNeeded() {
        if (userDAO.countAllByUserRoleEquals(UserRole.ADMIN) == 0) {
            User admin = new User(
                    null,
                    "admin",
                    "e879a6bad47bb50ef56a957edd1c3c78b26d77ebb8e5547cd71fdca0fdae834fbe7b914ad71e3359fdf1cc4f0934ddd145bcc8e1b3ac841efa4db2a48c1b120f",
                    "Admin",
                    "Admin",
                    null,
                    UserRole.ADMIN
            );
            userDAO.save(admin);
        }
    }

}