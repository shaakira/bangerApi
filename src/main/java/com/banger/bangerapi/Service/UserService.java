package com.banger.bangerapi.Service;

import com.banger.bangerapi.Exception.RunTimeException;
import com.banger.bangerapi.Models.Authority;
import com.banger.bangerapi.Models.AuthorityType;
import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserService(){

    }

    @Autowired
    private AuthorityService authorityService;

    public ResponseEntity<String> registerUser(User user){
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RunTimeException("User is Already Registered!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RunTimeException("User Email is Already Taken!", HttpStatus.BAD_REQUEST);
        }
        else{
            User userObject = new User(user.getCustomerName(),
                    user.getUserName(),
                    bCryptPasswordEncoder.encode(user.getPassword()),
                    user.getEmail()
            );
            Set<Authority> mappedAuthorities = new HashSet<>();
            Authority authority=authorityService.getRoleByName(AuthorityType.ROLE_CUSTOMER);
            mappedAuthorities.add(authority);
            userObject.setAuthorities(mappedAuthorities);
            userRepository.save(userObject);
            return new ResponseEntity<>("User Registered Successfully",HttpStatus.OK);
        }

    }

    public User getUser(String username){
        User user=userRepository.findByUserName(username);
        return user;
    }
    public ResponseEntity<String> updateUser(User user,String username){
        User existingUser=userRepository.findByUserName(username);
        existingUser.setCustomerName(user.getCustomerName());
        existingUser.setEmail(user.getEmail());
        userRepository.save(existingUser);
        return new ResponseEntity<>("User Updated Successfully",HttpStatus.OK);
    }
    public ResponseEntity<String> updatePassword(User user,String username){
        User existingUser=userRepository.findByUserName(username);
        existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(existingUser);
        return new ResponseEntity<>("Password Updated Successfully",HttpStatus.OK);
    }
    public List<User> getActiveUsers() {
        return userRepository.findActiveUsers();
    }
    @Transactional
    public ResponseEntity<String> blacklistUser(int id) {
        userRepository.blacklist(id);
        return new ResponseEntity<>("user blacklisted",HttpStatus.OK);
    }
    @Transactional
    public  ResponseEntity<String>  activateUser(int id) {
        userRepository.activate(id);
        return new ResponseEntity<>("user activated",HttpStatus.OK);
    }
    public List<User> getBlacklistedUsers() {
        return userRepository.findBlacklistUsers();
    }

}
