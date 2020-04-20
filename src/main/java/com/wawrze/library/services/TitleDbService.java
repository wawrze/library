package com.wawrze.library.services;

import com.wawrze.library.dao.BookDAO;
import com.wawrze.library.dao.TitleDAO;
import com.wawrze.library.domains.books.Book;
import com.wawrze.library.domains.titles.Title;
import com.wawrze.library.domains.titles.TitleDto;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.ForbiddenException;
import com.wawrze.library.exeptions.TitleNotFoundException;
import com.wawrze.library.mappers.BookMapper;
import com.wawrze.library.mappers.TitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TitleDbService {

    private final TitleDAO titleDAO;
    private final BookDAO bookDAO;
    private final BookMapper bookMapper;
    private final TitleMapper titleMapper;

    @Autowired
    public TitleDbService(TitleDAO titleDAO, BookDAO bookDAO, BookMapper bookMapper, TitleMapper titleMapper) {
        this.titleDAO = titleDAO;
        this.bookDAO = bookDAO;
        this.bookMapper = bookMapper;
        this.titleMapper = titleMapper;
    }

    public List<Title> getAllTitles() {
        return titleDAO.findAll();
    }

    public List<Title> getTitlesWithAvailableBooks() {
        return titleDAO.getTitlesWithAvailableBooks();
    }

    public TitleDto getTitle(final Integer id) throws TitleNotFoundException {
        Title title = titleDAO.findById(id).orElseThrow(TitleNotFoundException::new);
        List<Book> books = bookDAO.getBooksByTitleId(title.getId());
        return titleMapper.mapToTitleDto(title, bookMapper.mapToBookDtoList(books));
    }

    public Title saveTitle(final Title title, final UserRole userRole) {
        if (userRole != UserRole.ADMIN) throw new ForbiddenException("Only administrator can manage titles!");
        return titleDAO.save(title);
    }

    public void deleteTitle(final Integer id, final UserRole userRole) {
        if (userRole != UserRole.ADMIN) throw new ForbiddenException("Only administrator can manage titles!");
        titleDAO.delete(id);
    }

}