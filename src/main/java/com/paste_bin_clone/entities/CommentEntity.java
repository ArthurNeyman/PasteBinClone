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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PasteEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "paste_id")
    private PasteEntity paste;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
