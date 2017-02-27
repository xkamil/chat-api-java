package com.xkamil.storage;


import org.mongodb.morphia.annotations.PrePersist;

import java.util.Date;

public class Sentence {

    private String userId;
    private String content;
    private Date createdAt;

    public Sentence() {
    }

    public Sentence(String userId, String content) {
        this.userId = userId;
        this.content = content;
        this.createdAt = new Date();
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

