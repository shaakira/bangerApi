package com.banger.bangerapi;

import com.banger.bangerapi.Models.Contact;
import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Repository.ContactRepository;
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
public class ContactRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void findById() {
        Contact contact=new Contact("testName" ,"testEmail","testSubject","testMessage");
        contact= testEntityManager.persistAndFlush(contact);
        Contact foundContact = contactRepository.findById(contact.getId()).get();
        assertEquals(foundContact.getId(), contact.getId());
    }

    @Test
    public void testContactListIsNotNull(){
        List<Contact> contacts=contactRepository.findAll();
        assertThat(contacts).size().isGreaterThan(0);
    }



}
