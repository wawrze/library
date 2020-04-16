package com.wawrze.library.services;

import com.wawrze.library.dao.TitleDAO;
import com.wawrze.library.domains.titles.Title;
import com.wawrze.library.domains.users.UserRole;
import com.wawrze.library.exeptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TitleDbService {

    private final TitleDAO titleDAO;

    @Autowired
    public TitleDbService(TitleDAO titleDAO) {
        this.titleDAO = titleDAO;
    }

    public List<Title> getAllTitles() {
        return titleDAO.findAll();
    }

    public Optional<Title> getTitle(final Integer id) {
        return titleDAO.findById(id);
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