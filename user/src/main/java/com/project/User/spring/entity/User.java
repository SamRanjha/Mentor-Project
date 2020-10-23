package com.project.User.spring.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Users")
public class User {
    @Id
    private long id;

    @Column
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String contact;

    @Column
    private Date reg_date;

    @Column
    private String reg_code;

    @Column
    private String type;

    @Column
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_code() {
        return reg_code;
    }

    public void setReg_code(String reg_code) {
        this.reg_code = reg_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}


