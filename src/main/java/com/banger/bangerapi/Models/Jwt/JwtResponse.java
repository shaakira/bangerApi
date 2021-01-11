package com.banger.bangerapi.Models.Jwt;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;
    private String status;

    public JwtResponse(String token, String username,List<String> roles,String status) {
        this.token = token;
        this.username = username;
        this.roles=roles;
        this.status=status;
    }
}
