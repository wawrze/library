package com.wawrze.library.mappers;

import com.wawrze.library.domains.books.BookDto;
import com.wawrze.library.domains.titles.Title;
import com.wawrze.library.domains.titles.TitleDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TitleMapper {

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(titleDto.getId(), titleDto.getTitle(), titleDto.getAuthor(), titleDto.getPublicationYear());
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(title.getId(), title.getTitle(), title.getAuthor(), title.getPublicationYear(), new ArrayList<>());
    }

    public TitleDto mapToTitleDto(final Title title, final List<BookDto> books) {
        return new TitleDto(title.getId(), title.getTitle(), title.getAuthor(), title.getPublicationYear(), books);
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titleList) {
        return titleList.stream()
                .map(this::mapToTitleDto)
                .collect(Collectors.toList());
    }

}