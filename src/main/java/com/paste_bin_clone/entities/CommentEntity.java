package com.paste_bin_clone.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private String text;
    @Column(name = "paste_id")
    private long pasteId;
    @Column (name = "user_id")
    private long userId;
}
