package com.wawrze.library;

import com.wawrze.library.domains.users.User;
import com.wawrze.library.exeptions.UserNotFoundException;
import com.wawrze.library.repositories.UserRepository;
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
public class UserRepositoryTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        //Given
        User user1 = new User(null, "", "", "John", "Smith", null);
        User user2 = new User(null, "", "", "Paul", "Johnes", null);
        int id1;
        int id2;
        List<User> resultList;
        //When
        userRepository.save(user1);
        userRepository.save(user2);
        id1 = user1.getId();
        id2 = user2.getId();
        resultList = userRepository.findAll();
        //Then
        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void testFindById() {
        //Given
        User user = new User(null, "", "", "John", "Smith", null);
        int id;
        User resultUser1 = null;
        User resultUser2;
        boolean resultNoUser = false;
        //When
        userRepository.save(user);
        id = user.getId();
        try {
            resultUser1 = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        } catch (UserNotFoundException e) {
        }
        try {
            resultUser2 = userRepository.findById(0).orElseThrow(UserNotFoundException::new);
        } catch (UserNotFoundException e) {
            resultNoUser = true;
        }
        //Then
        Assert.assertEquals(resultUser1.getId(), user.getId());
        Assert.assertEquals(resultUser1.getFirstName(), user.getFirstName());
        Assert.assertEquals(resultUser1.getLastName(), user.getLastName());
        Assert.assertEquals(resultUser1.getAccountCreationDate(), user.getAccountCreationDate());
        Assert.assertTrue(resultNoUser);
    }

}