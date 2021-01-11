package com.banger.bangerapi.Models;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class Claims {
    @Id
    private Integer id;
    private String name;
    private String nic;

    public Claims(String name, String nic) {
        this.name = name;
        this.nic = nic;
    }

    public Claims() {
    }
}
