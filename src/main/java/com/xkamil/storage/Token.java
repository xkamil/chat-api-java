package com.xkamil.storage;

import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.annotations.*;

import java.util.Date;
import java.util.UUID;

@Entity("tokens")
public class Token {
    @Id private ObjectId objectId;

    private String userId;
    private String token;
    private Long updatedAt;


    @PrePersist
    private void beforeSave() {
        this.updatedAt = new Date().getTime();
        if(token == null){
            this.token = generateToken();
        }
    }


    public Token() {
    }

    public Token(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String generateToken() {
        return BCrypt.hashpw(UUID.randomUUID().toString(), BCrypt.gensalt());
    }

}
