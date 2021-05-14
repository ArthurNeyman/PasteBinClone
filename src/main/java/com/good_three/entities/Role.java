package com.good_three.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="roles")
@JsonIgnoreProperties("users")
public class Role {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private  Long id;

    @Column(name="name")
    private  String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<UserEntity> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }


}
