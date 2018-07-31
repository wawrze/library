package com.wawrzelibrary.library;

import com.wawrzelibrary.library.domains.titles.Title;
import com.wawrzelibrary.library.exeptions.TitleNotFoundException;
import com.wawrzelibrary.library.repositories.TitleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TitleRepositoryTestSuite {

    @Autowired
    TitleRepository titleRepository;

    @Test
    public void testTitleFindAll() {
        //Given
        Title title1 = new Title("Some title", "John Smith", 2000);
        Title title2 = new Title("Other title", "Paul Jones", 2010);
        int id1;
        int id2;
        List<Title> resultList;
        //When
        titleRepository.save(title1);
        titleRepository.save(title2);
        id1 = title1.getId();
        id2 = title2.getId();
        resultList = titleRepository.findAll();
        //Then
        Assert.assertEquals(2, resultList.size());
        //CleanUp
        titleRepository.delete(id1);
        titleRepository.delete(id2);
    }

    @Test
    public void testFindById() {
        //Given
        Title title = new Title("Some Title", "John Smith", 2000);
        int id;
        Title resultTitle1 = null;
        Title resultTitle2;
        boolean resultNoTitle = false;
        //When
        titleRepository.save(title);
        id = title.getId();
        try {
            resultTitle1 = titleRepository.findById(id).orElseThrow(TitleNotFoundException::new);
        }
        catch(TitleNotFoundException e) {}
        try {
            resultTitle2 = titleRepository.findById(0).orElseThrow(TitleNotFoundException::new);
        }
        catch (TitleNotFoundException e) {
            resultNoTitle = true;
        }
        //Then
        Assert.assertEquals(resultTitle1.getId(), title.getId());
        Assert.assertEquals(resultTitle1.getTitle(), title.getTitle());
        Assert.assertEquals(resultTitle1.getAuthor(), title.getAuthor());
        Assert.assertEquals(resultTitle1.getPublicationYear(), title.getPublicationYear());
        Assert.assertTrue(resultNoTitle);
        //CleanUp
        titleRepository.delete(id);
    }

}