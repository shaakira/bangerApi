package com.banger.bangerapi.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter
@Getter
@Entity
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private AuthorityType name;
    private String email;
    private String userName;
    private String password;
    public Authority() {}



    // getters and setters
}
