package com.paste_bin_clone.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@JsonIgnoreProperties("paste")
@Data
@Accessors(chain = true)
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
