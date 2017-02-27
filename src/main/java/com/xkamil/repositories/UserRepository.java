package com.xkamil.repositories;

import com.xkamil.handlers.exceptions.ItemNotFoundException;
import com.xkamil.storage.Token;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.xkamil.storage.User;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private Datastore datastore;

    public List<User> findAll() throws Exception {

        List<User> users = datastore.createQuery(User.class).asList();

        return users;
    }

    public User findById(String id) throws ItemNotFoundException {

        return datastore.get(User.class, new ObjectId(id));
    }

    public User findByName(String username) {

        return datastore.find(User.class)
                .field("name")
                .equal(username)
                .get();
    }

    public Token add(String name, String password) {

        User user = new User(name, password);
        Key userKey = datastore.save(user);

        Token token = new Token();
        token.setUserId(userKey.getId().toString());
        Key key = datastore.save(token);

        return token;
    }
}
