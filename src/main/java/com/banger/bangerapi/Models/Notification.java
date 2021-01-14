package com.banger.bangerapi.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String notification;
    private String time;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY,cascade=
            {CascadeType.DETACH,  CascadeType.MERGE,  CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user", nullable = false)
    private User user;



    public Notification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
