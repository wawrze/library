package com.wawrzelibrary.library.mappers;

import com.wawrzelibrary.library.domains.users.User;
import com.wawrzelibrary.library.domains.users.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return new User(userDto.getId(), userDto.getFirstName(), userDto.getLastName(),
                userDto.getAccountCreationDate());
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getAccountCreationDate());
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName(),
                        user.getAccountCreationDate()))
                .collect(Collectors.toList());
    }

}