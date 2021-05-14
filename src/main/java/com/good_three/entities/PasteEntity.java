package com.good_three.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="paste")
public class PasteEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @Column(name="hash_code")
    private String hashCode;

    @Column(name="date_create")
    private Date dateCreate;

    @ManyToOne
    @JoinColumn(name="access_id", nullable=false)
    private AccessEntity access;

    @ManyToOne
    @JoinColumn(name="lifetime_id", nullable=false)
    private LifeTimeEntity lifetime;

    @Column(name="dead_time")
    private Date deadTime;

    @OneToMany(mappedBy = "paste",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    private String name;

    private String description;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccessEntity getAccess() {
        return access;
    }

    public void setAccess(AccessEntity access) {
        this.access = access;
    }

    public LifeTimeEntity getLifetime() {
        return lifetime;
    }

    public void setLifetime(LifeTimeEntity lifetime) {
        this.lifetime = lifetime;
    }

    public Date getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(Date deadTime) {
        this.deadTime = deadTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}
