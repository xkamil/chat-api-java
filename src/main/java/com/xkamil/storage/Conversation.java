package com.xkamil.storage;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity("conversations")
public class Conversation {
    @Id private ObjectId objectId;
    @Transient private String id;

    private List<String> users;
    private Date createdAt;
    private Date updatedAt;
    private List<Sentence> sentences = new ArrayList<>();

    @PostLoad
    private void afterLoad() {
        id = objectId.toHexString();
    }

    @PrePersist
    private void beforeSave() {
        if(createdAt == null){
            createdAt = new Date();
        }

        updatedAt = new Date();
    }

    public Conversation() {
    }

    public String getId() {
        return id;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void addSentence(Sentence sentence){
        this.sentences.add(sentence);
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }
}

