package com.good_three.entities;

import javax.persistence.*;

@Entity
@Table(name="lifetime")
public class LifeTimeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private String name;
    private long minutes;

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

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }
}
