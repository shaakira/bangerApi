package com.banger.bangerapi;

import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Repository.UserRepository;
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
        User user = new User();
        user = testEntityManager.persistAndFlush(user);
        User foundUser = userRepository.findByUserName(user.getUserName());
        assertEquals(foundUser.getUserName(), user.getUserName());
    }
    @Test
    public  void  testUserList(){
        List<User> foundUsers=userRepository.findAll();
        assertThat(foundUsers).size().isGreaterThan(0);
    }
    @Test
    public void testUpdateName(){
    User user=new User("testName", "testUsername", "testPassword", "testEmail");
    User user1=userRepository.save(user);
    user1.setCustomerName("name");
    userRepository.save(user1);
    User foundUser=userRepository.findById(user.getId()).get();
    assertEquals(foundUser.getCustomerName(),"name");

    }
}
