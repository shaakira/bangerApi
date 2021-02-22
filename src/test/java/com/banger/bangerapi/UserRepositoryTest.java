package com.banger.bangerapi;

import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findById() {
        User user = new User("testName", "testUsername", "testPassword", "testEmail");
        user = testEntityManager.persistAndFlush(user);
        User foundUser = userRepository.findById(user.getId()).get();
        assertEquals(foundUser.getId(), user.getId());

    }

    @Test
    public void findByUsername() {
       String name="testUsername";
        User foundUser = userRepository.findByUserName(name);

        assertEquals(foundUser.getUserName(), name);
    }
    @Test
    public  void  testUserList(){
        List<User> foundUsers=userRepository.findAll();
        assertThat(foundUsers).size().isGreaterThan(0);
    }
    @Test
    public void testUpdateName(){
        String name="testUsername";
        User user1 = userRepository.findByUserName(name);
        user1.setCustomerName("name");
    userRepository.save(user1);
    User foundUser=userRepository.findByUserName(name);
    assertEquals(foundUser.getCustomerName(),"name");

    }
    @Test
    public  void  testActiveUserList(){
        List<User> foundUsers=userRepository.findActiveUsers();
        assertThat(foundUsers).size().isGreaterThan(0);
    }
    @Test
    public void testDeleteUser(){
        User user1 = userRepository.findByUserName("testUsername");
         userRepository.deleteById(user1.getId());
        User foundUser=userRepository.findByUserName(user1.getUserName());
        org.junit.jupiter.api.Assertions.assertNull(foundUser);
    }
    @Test
    public  void  testBlackUserList(){
        List<User> foundUsers=userRepository.findBlacklistUsers();
        assertThat(foundUsers).size().isEqualTo(2);
    }

    @Test
    public void testBlackIsNotNull(){
        List<User> foundUsers=userRepository.findBlacklistUsers();
        assertThat(foundUsers).size().isGreaterThan(0);
    }
    @Test
    public void findByEmail() {
        String email="testEmail";
        User foundUser = userRepository.findByEmail(email);
        assertEquals(foundUser.getEmail(), email);
    }
    @Test
    public void testBlackList(){
        User user=userRepository.findByUserName("testUsername");
        userRepository.blacklist(user.getId());
        User foundUser=userRepository.findByUserName("testUsername");
        assertEquals(foundUser.getStatus(),"blacklist");
    }
    @Test
    public void testActivate(){
        User user=userRepository.findByUserName("testUsername");
        userRepository.activate(user.getId());
        User foundUser=userRepository.findByUserName("testUsername");
        assertEquals("active",foundUser.getStatus());
    }

}
