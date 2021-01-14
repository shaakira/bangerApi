package com.banger.bangerapi.Models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
public class User {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String customerName;
    private String userName;
    private String password;
    private String email;
    private String licenseImage;
    private String utilityImage;
    private String status="active";
    private String type="customer";
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade=
                    {CascadeType.DETACH,  CascadeType.MERGE,  CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Notification> notifications;
    public User() {

    }

    public User(String customerName, String userName, String password, String email) {
        this.customerName = customerName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getLicenseImage() {
        return licenseImage;
    }

    public void setLicenseImage(String licenseImage) {
        this.licenseImage = licenseImage;
    }

    public String getUtilityImage() {
        return utilityImage;
    }

    public void setUtilityImage(String utilityImage) {
        this.utilityImage = utilityImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id") })
    private Set<Authority> authorities = new HashSet<>();
}
