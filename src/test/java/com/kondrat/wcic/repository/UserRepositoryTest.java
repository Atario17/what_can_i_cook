package com.kondrat.wcic.repository;

import com.kondrat.wcic.controllers.CreateUserRequest;
import com.kondrat.wcic.domain.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest

public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
        userRepository.flush();
        userRepository.save(new User(1, "Ivanov", "iii"));
        userRepository.save(new User(2, "Sidorov", "sss"));
    }

    @Test
    public void createIfNotExistsTest(){
        //bbb
        assertTrue(userRepository.createIfNotExists(new User((int) (Math.random() * 1000000),"Vasya", "123")));
        assertEquals(3,userRepository.findAll().size());
        assertFalse(userRepository.createIfNotExists( new User((int) (Math.random() * 1000000),"Vasya", "123")));
        assertEquals(3,userRepository.findAll().size());
        assertTrue(userRepository.createIfNotExists( new User((int) (Math.random() * 1000000),"Lera", "ggg")));
        assertEquals(4,userRepository.findAll().size());

    }
    @Test
    public void  dataVerificationTest(){
        assertTrue(userRepository.dataVerification(CreateUserRequest.createUser("Ivanov", "iii")));
        assertFalse(userRepository.dataVerification(CreateUserRequest.createUser("Ivanov", "ii")));
        assertFalse(userRepository.dataVerification(CreateUserRequest.createUser("Vasek", "1234567")));
        assertEquals(2,userRepository.findAll().size());
    }
    @Test
    public void userPresentTest(){
        assertFalse(userRepository.userPresent("Vasya"));
        assertTrue(userRepository.userPresent("Ivanov"));
    }
}
