package com.banger.bangerapi.Service;

import com.banger.bangerapi.Models.Contact;
import com.banger.bangerapi.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    public ContactService(){
    }
    public ResponseEntity<String> addContact(Contact contact){

        Contact contact1=new Contact(contact.getName(),contact.getEmail(),contact.getSubject(),contact.getMessage());
        contactRepository.save(contact1);
        return new ResponseEntity<>("Successfully submitted",HttpStatus.OK);
    }
    public List<Contact> getAllContacts(){
        List<Contact> contacts=contactRepository.findAll();
        return contacts;
    }
    public ResponseEntity<String> deleteNotification(int id){
        contactRepository.deleteById(id);
        return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
    }
}
