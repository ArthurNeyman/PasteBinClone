package com.good_three.entities;

import javax.persistence.*;

@Entity
@Table(name="access")
public class AccessEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String name;

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
}
