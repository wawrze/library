package com.wawrze.library.mappers;

import com.wawrze.library.domains.rents.Rent;
import com.wawrze.library.domains.rents.RentDto;
import com.wawrze.library.domains.rents.SimplifiedRentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentMapper {

    private final BookMapper bookMapper;
    private final UserMapper userMapper;

    @Autowired
    public RentMapper(BookMapper bookMapper, UserMapper userMapper) {
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
    }

    public Rent mapToRent(final RentDto rentDto) {
        return new Rent(
                rentDto.getId(),
                userMapper.mapToUser(rentDto.getUser()),
                bookMapper.mapToBook(rentDto.getBook()),
                rentDto.getRentStartDate(),
                rentDto.getRentFinishDate()
        );
    }

    public RentDto mapToRentDto(final Rent rent) {
        return new RentDto(
                rent.getId(),
                userMapper.mapToUserDto(rent.getUser()),
                bookMapper.mapToBookDto(rent.getBook()),
                rent.getRentStartDate(),
                rent.getRentFinishDate()
        );
    }

    private SimplifiedRentDto mapToSimplifiedRentDto(final Rent rent) {
        return new SimplifiedRentDto(
                rent.getId(),
                rent.getUser().getId(),
                bookMapper.mapToBookDto(rent.getBook()),
                rent.getRentStartDate(),
                rent.getRentFinishDate()
        );
    }

    public List<RentDto> mapToRentDtoList(final List<Rent> rentList) {
        return rentList.stream()
                .map(this::mapToRentDto)
                .collect(Collectors.toList());
    }

    public List<SimplifiedRentDto> mapToSimplifiedRentDtoList(final List<Rent> rentList) {
        return rentList.stream()
                .map(this::mapToSimplifiedRentDto)
                .collect(Collectors.toList());
    }

}