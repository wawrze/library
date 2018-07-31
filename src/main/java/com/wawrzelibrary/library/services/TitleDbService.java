package com.wawrzelibrary.library.services;

import com.wawrzelibrary.library.domains.titles.Title;
import com.wawrzelibrary.library.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TitleDbService {

    @Autowired
    private TitleRepository titleRepository;

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Optional<Title> getTitle(final Integer id) {
        return titleRepository.findById(id);
    }

    public Title saveTitle(final Title title) {
        return titleRepository.save(title);
    }

    public void deleteTitle(final Integer id) {
        titleRepository.delete(id);
    }

}