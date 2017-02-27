package com.xkamil.repositories;


import com.xkamil.handlers.exceptions.ItemNotFoundException;
import com.xkamil.storage.Token;
import com.xkamil.storage.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class TokenRepository {

    @Autowired
    private Datastore datastore;

    public Token findByUserId(String userId) throws ItemNotFoundException {

        return datastore.find(Token.class)
                .field("userId")
                .equal(userId)
                .get();
    }

    public User findUserByToken(String sToken) {

        Token token = findByToken(sToken);

        if (token != null) {
            return datastore.get(User.class, new ObjectId(token.getUserId()));
        } else {
            return null;
        }
    }

    public Token findByToken(String token) {

        return datastore.find(Token.class)
                .field("token")
                .equal(token)
                .get();
    }

    public Token addToken(String userId) throws ItemNotFoundException {
        User user = datastore.get(User.class, new ObjectId(userId));

        if (user == null) {
            throw new ItemNotFoundException("No user found with id: " + userId);
        }

        Token token = datastore.find(Token.class)
                .field("userId")
                .equal(user.getId())
                .get();

        if (token == null) {
            token = new Token();
            token.setUserId(user.getId());
        }

        datastore.save(token);

        return token;
    }

    public Token refreshToken(String sToken) {
        Token token = findByToken(sToken);

        if (token == null) {
            return null;
        }

        token.setUpdatedAt(new Date().getTime());
        datastore.save(token);

        return token;
    }
}
