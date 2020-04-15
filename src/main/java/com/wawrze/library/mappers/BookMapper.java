package com.wawrze.library.mappers;

import com.wawrze.library.domains.books.Book;
import com.wawrze.library.domains.books.BookDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book mapToBook(final BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle(), bookDto.getStatus());
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getStatus());
    }

    public List<BookDto> mapToBookDtoList(final List<Book> bookList) {
        return bookList.stream()
                .map(book -> new BookDto(book.getId(), book.getTitle(), book.getStatus()))
                .collect(Collectors.toList());
    }

}