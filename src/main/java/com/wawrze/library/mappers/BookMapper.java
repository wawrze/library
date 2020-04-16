package com.wawrze.library.mappers;

import com.wawrze.library.domains.books.Book;
import com.wawrze.library.domains.books.BookDto;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book mapToBook(final BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle());
    }

    public BookDto mapToBookDto(final Book book) {
        Date rentFinish = null;
        try {
            rentFinish = book.getRent().getRentFinishDate();
        } catch (NullPointerException ignored) {
        }
        return new BookDto(book.getId(), book.getTitle(), rentFinish);
    }

    public List<BookDto> mapToBookDtoList(final List<Book> bookList) {
        return bookList.stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

}