package com.wawrze.library.controllers;

import com.wawrze.library.domains.books.BookDto;
import com.wawrze.library.exeptions.BookNotFoundException;
import com.wawrze.library.mappers.BookMapper;
import com.wawrze.library.services.BookDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookDbService service;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookDbService service, BookMapper bookMapper) {
        this.service = service;
        this.bookMapper = bookMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBooks")
    public List<BookDto> getBooks() {
        return bookMapper.mapToBookDtoList(service.getAllBooks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBook")
    public BookDto getBook(@RequestParam Integer bookId) throws BookNotFoundException {
        return bookMapper.mapToBookDto(service.getBook(bookId).orElseThrow(BookNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBook")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        return bookMapper.mapToBookDto(service.saveBook(bookMapper.mapToBook(bookDto)));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createBook",
            consumes = APPLICATION_JSON_VALUE
    )
    public void createBook(@RequestBody BookDto bookDto) {
        service.saveBook(bookMapper.mapToBook(bookDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBook")
    public void deleteBook(@RequestParam Integer bookId) {
        service.deleteBook(bookId);
    }

}