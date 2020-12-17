package com.banger.bangerapi.Service;

import com.banger.bangerapi.Models.Authority;
import com.banger.bangerapi.Models.AuthorityType;
import com.banger.bangerapi.Repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority getRoleByName(AuthorityType roleName) {
        return authorityRepository.findByName(roleName);
    }

}
