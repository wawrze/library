package com.wawrze.library.mappers;

import com.wawrze.library.domains.rents.Rent;
import com.wawrze.library.domains.rents.RentDto;
import com.wawrze.library.domains.users.User;
import com.wawrze.library.domains.users.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private BookMapper bookMapper;

    @Autowired
    public UserMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getAccountCreationDate(),
                userDto.getUserRole()
        );
    }

    public UserDto mapToUserDto(final User user) {
        Set<RentDto> rents = new HashSet<>();
        if (user.getRents() != null && user.getRents().size() > 0) {
            rents = mapToRentDtoList(user.getRents());
        }

        return new UserDto(
                user.getId(),
                user.getLogin(),
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getAccountCreationDate(),
                user.getUserRole(),
                null,
                rents
        );
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private RentDto mapToRentDto(final Rent rent) {
        return new RentDto(
                rent.getId(),
                null,
                bookMapper.mapToBookDto(rent.getBook()),
                rent.getRentStartDate(),
                rent.getRentFinishDate()
        );
    }

    private Set<RentDto> mapToRentDtoList(final Set<Rent> rentList) {
        return rentList.stream()
                .map(this::mapToRentDto)
                .collect(Collectors.toSet());
    }

}