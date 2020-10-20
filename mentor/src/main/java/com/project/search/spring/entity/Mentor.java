package com.project.search.spring.entity;


import javax.persistence.*;

@Entity
@Table(name = "Mentor")
public class Mentor {
    @Id
    private long mid;

    @Column
    private String username;

    @Column
    private String linkedin_url;

    @Column
    private String reg_datetime;

    @Column
    private String reg_code;

    @Column
    private Float years_of_exp;

    @Column
    private boolean active;

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLinkedin_url() {
        return linkedin_url;
    }

    public void setLinkedin_url(String linkedin_url) {
        this.linkedin_url = linkedin_url;
    }

    public String getReg_datetime() {
        return reg_datetime;
    }

    public void setReg_datetime(String reg_datetime) {
        this.reg_datetime = reg_datetime;
    }

    public String getReg_code() {
        return reg_code;
    }

    public void setReg_code(String reg_code) {
        this.reg_code = reg_code;
    }

    public Float getYears_of_exp() {
        return years_of_exp;
    }

    public void setYears_of_exp(Float years_of_exp) {
        this.years_of_exp = years_of_exp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
