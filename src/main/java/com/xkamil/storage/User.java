package com.xkamil.storage;

import lombok.*;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

import org.mongodb.morphia.annotations.*;


@Entity("users")
public class User {
    @Id
    protected ObjectId objectId;

    private String name;
    private String password;

    @Transient
    private String id;

    @PostLoad
    private void postLoad() {
        this.id = this.objectId.toHexString();
    }

    @PrePersist
    private void beforePersist() {
        this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
    }

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean isPasswordMatch(String password){
        return BCrypt.checkpw(password, this.password);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

