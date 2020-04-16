package com.wawrze.library.mappers;

import com.wawrze.library.domains.rents.Rent;
import com.wawrze.library.domains.rents.RentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentMapper {

    public Rent mapToRent(final RentDto rentDto) {
        return new Rent(rentDto.getId(), rentDto.getUser(), rentDto.getBook(), rentDto.getRentStartDate(),
                rentDto.getRentFinishDate());
    }

    public RentDto mapToRentDto(final Rent rent) {
        return new RentDto(rent.getId(), rent.getUser(), rent.getBook(), rent.getRentStartDate(),
                rent.getRentFinishDate());
    }

    public List<RentDto> mapToRentDtoList(final List<Rent> rentList) {
        return rentList.stream()
                .map(this::mapToRentDto)
                .collect(Collectors.toList());
    }

}