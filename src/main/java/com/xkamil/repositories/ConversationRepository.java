package com.xkamil.repositories;

import com.xkamil.handlers.exceptions.BadRequestException;
import com.xkamil.storage.Conversation;
import com.xkamil.storage.Sentence;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConversationRepository {

    @Autowired
    private Datastore datastore;

    public List<Conversation> findAll() throws Exception {

        return datastore.createQuery(Conversation.class).asList();
    }

    public List<Conversation> findForUsersStrict(List<String> usersIds) {

        return datastore.find(Conversation.class)
                .field("users")
                .sizeEq(usersIds.size())
                .field("users")
                .hasAllOf(usersIds)
                .asList();
    }

    public List<Conversation> findForUsers(List<String> usersIds) {

        return datastore.find(Conversation.class)
                .field("users")
                .hasAllOf(usersIds)
                .asList();
    }

    public List<Conversation> findForUser(String userId) {

        return datastore.find(Conversation.class)
                .field("users")
                .hasThisOne(userId)
                .asList();
    }

    public Conversation add(List<String> usersIds) throws BadRequestException {

        if (usersIds.size() < 2) {
            throw new BadRequestException("There mus me at least 2 users in conversation. Current: " + usersIds.size());
        }

        for (int i = 0; i < usersIds.size(); i++) {
            for (int j = 0; j < usersIds.size(); j++) {
                if (i != j) {
                    if (usersIds.get(i).equals(usersIds.get(j))) {
                        throw new BadRequestException("Duplicated users ids in request");
                    }
                }
            }
        }

        Conversation conversation = new Conversation();
        conversation.setUsers(usersIds);

        Key key = datastore.save(conversation);

        return datastore.get(Conversation.class, new ObjectId(key.getId().toString()));
    }

    public Conversation addSentence(String conversationId, String userId, String content){
        Conversation conversation = datastore.get(Conversation.class, new ObjectId(conversationId));

        if(conversation != null){
            Sentence sentence = new Sentence(userId, content);
            conversation.addSentence(sentence);
            Key key = datastore.save(conversation);

            return datastore.get(Conversation.class, new ObjectId(key.getId().toString()));
        }

        return null;
    }

}
