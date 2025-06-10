package com.paste_bin_clone.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paste")
@Data
@Accessors(chain = true)
public class PasteEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @Column(name = "hash_code", updatable = false)
    private String hashCode;

    @Column(name = "date_create")
    private Instant dateCreate;

    @Column(name = "access")
    private String access;

    @Column(name = "lifetime")
    private String lifetime;

    @Column(name = "dead_time")
    private Instant deadTime;

    @OneToMany(mappedBy = "pasteId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CommentEntity> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String name;

    private String description;

}
