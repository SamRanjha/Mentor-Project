package com.project.technologies.spring.entity;


import javax.persistence.*;

@Entity
@Table(name = "Technologies")
public class Technologies {
    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String toc;

    @Column
    private String duration;

    @Column
    private String prerequites;

    @Column
    private boolean active;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToc() {
        return toc;
    }

    public void setToc(String toc) {
        this.toc = toc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrerequites() {
        return prerequites;
    }

    public void setPrerequites(String prerequites) {
        this.prerequites = prerequites;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
