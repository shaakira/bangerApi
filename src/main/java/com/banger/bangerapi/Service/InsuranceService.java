package com.banger.bangerapi.Service;
import com.banger.bangerapi.Exception.CustomException;
import com.banger.bangerapi.Models.Claims;
import com.banger.bangerapi.Repository.ClaimsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceService {
    @Autowired
    ClaimsRepository claimsRepository;

    public boolean validateNIC(String nic){
        List<Claims> claimsList =claimsRepository.findAll();
        boolean flag=true;
        for (Claims claims:claimsList) {
            if(claims.getNic().equals(nic)){
                flag=false;
            }
        }
        return flag;
    }
}
