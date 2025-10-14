package com.paste_bin_clone.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paste")
@Data
@Accessors(chain = true)
public class PasteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paste_seq")
    @SequenceGenerator(name = "paste_seq", sequenceName = "paste_seq", allocationSize = 1)
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
