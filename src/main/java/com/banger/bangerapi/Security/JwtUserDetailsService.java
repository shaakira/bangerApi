package com.banger.bangerapi.Security;


import java.util.ArrayList;

import com.banger.bangerapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.banger.bangerapi.Models.User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        //log.info("loadUserByUsername() : {}", username);
        return  new BangerUserDetails(user);

    }
}
