package com.good_three.dto;

import java.util.Date;
import java.util.List;

public class PasteDTO {

    private long id;
    private String hashCode;
    private Date dateCreate;
    private String name;
    private long accessId;
    private long lifetimeId;
    private Date deadTime;
    private String description;
    private List<CommentDTO> comments;
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
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

    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(long accessId) {
        this.accessId = accessId;
    }

    public long getLifetimeId() {
        return lifetimeId;
    }

    public void setLifetimeId(long lifetimeId) {
        this.lifetimeId = lifetimeId;
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
}
