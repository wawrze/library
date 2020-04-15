package com.wawrzelibrary.library;

import com.wawrzelibrary.library.domains.books.Book;
import com.wawrzelibrary.library.domains.titles.Title;
import com.wawrzelibrary.library.exeptions.BookNotFoundException;
import com.wawrzelibrary.library.repositories.BookRepository;
import com.wawrzelibrary.library.repositories.TitleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Profile("test")
public class BookRepositoryTestSuite {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    TitleRepository titleRepository;

    @Test
    public void testBookFindAll() {
        //Given
        Title title = new Title("Some title", "John Smith", 2000);
        Book book1;
        Book book2;
        int bookId1;
        int bookId2;
        int titleId;
        List<Book> resultList;
        //When
        titleRepository.save(title);
        titleId = title.getId();
        book1 = new Book(title, "Some_Status");
        book2 = new Book(title, "Other_Status");
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookId1 = book1.getId();
        bookId2 = book2.getId();
        resultList = bookRepository.findAll();
        //Then
        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void testFindById() {
        //Given
        Title title = new Title("Some Title", "John Smith", 2000);
        Book book;
        int bookId;
        int titleId;
        Book resultBook1 = null;
        Book resultBook2;
        boolean resultNoBook = false;
        //When
        titleRepository.save(title);
        titleId = title.getId();
        book = new Book(title, "some status");
        bookRepository.save(book);
        bookId = book.getId();
        try {
            resultBook1 = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        } catch (BookNotFoundException e) {
        }
        try {
            resultBook2 = bookRepository.findById(0).orElseThrow(BookNotFoundException::new);
        } catch (BookNotFoundException e) {
            resultNoBook = true;
        }
        //Then
        Assert.assertEquals(resultBook1.getId(), book.getId());
        Assert.assertEquals(resultBook1.getTitle().getId(), book.getTitle().getId());
        Assert.assertEquals(resultBook1.getStatus(), book.getStatus());
        Assert.assertTrue(resultNoBook);
    }

}