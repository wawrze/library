package com.wawrze.library.services;

import com.wawrze.library.domains.titles.Title;
import com.wawrze.library.exeptions.ForbiddenException;
import com.wawrze.library.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TitleDbService {

    private final TitleRepository titleRepository;

    @Autowired
    public TitleDbService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Optional<Title> getTitle(final Integer id) {
        return titleRepository.findById(id);
    }

    public Title saveTitle(final Title title, final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage titles!");
        return titleRepository.save(title);
    }

    public void deleteTitle(final Integer id, final boolean isAdmin) {
        if (!isAdmin) throw new ForbiddenException("Only administrator can manage titles!");
        titleRepository.delete(id);
    }

}