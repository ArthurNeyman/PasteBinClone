package com.good_three.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="comment")
@JsonIgnoreProperties("paste")
public class CommentEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PasteEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name="paste_id")
    private PasteEntity paste;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(name="user_id")
    private UserEntity user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PasteEntity getPaste() {
        return paste;
    }

    public void setPaste(PasteEntity paste) {
        this.paste = paste;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
