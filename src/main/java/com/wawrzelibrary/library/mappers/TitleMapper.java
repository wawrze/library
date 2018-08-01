package com.wawrzelibrary.library.mappers;

        import com.wawrzelibrary.library.domains.titles.Title;
        import com.wawrzelibrary.library.domains.titles.TitleDto;
        import org.springframework.stereotype.Component;

        import java.util.List;
        import java.util.stream.Collectors;

@Component
public class TitleMapper {

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(titleDto.getId(), titleDto.getTitle(), titleDto.getAuthor(), titleDto.getPublicationYear());
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(title.getId(), title.getTitle(), title.getAuthor(), title.getPublicationYear());
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titleList) {
        return titleList.stream()
                .map(title -> new TitleDto(title.getId(), title.getTitle(), title.getAuthor(),
                        title.getPublicationYear()))
                .collect(Collectors.toList());
    }

}