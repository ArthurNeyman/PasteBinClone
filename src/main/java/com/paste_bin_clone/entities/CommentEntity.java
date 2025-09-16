package com.paste_bin_clone.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
@Data
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private String text;
    @Column (name = "paste_id")
    private long pasteId;
    @Column (name = "user_id")
    private long userId;
}
