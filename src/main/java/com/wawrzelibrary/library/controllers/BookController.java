package com.wawrzelibrary.library.controllers;

import com.wawrzelibrary.library.domains.books.BookDto;
import com.wawrzelibrary.library.exeptions.BookNotFoundException;
import com.wawrzelibrary.library.mappers.BookMapper;
import com.wawrzelibrary.library.services.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private DbService service;

    @Autowired
    private BookMapper bookMapper;

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