package com.paste_bin_clone.entities;

import com.paste_bin_clone.other.ACCESS_LEVEL;
import com.paste_bin_clone.other.LIFETIME;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "paste")
@Data
@Accessors(chain = true)
public class PasteEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @Column(name = "hash_code")
    private String hashCode;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "access")
    private String access;

    @Column(name = "lifetime")
    private String lifetime;

    @Column(name = "dead_time")
    private LocalDateTime deadTime;

    @OneToMany(mappedBy = "paste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String name;

    private String description;

}
