package com.xkamil.controllers;


import com.xkamil.handlers.exceptions.ItemAlreadyExistsException;
import com.xkamil.handlers.exceptions.ItemNotFoundException;
import com.xkamil.repositories.ConversationRepository;
import com.xkamil.repositories.UserRepository;
import com.xkamil.storage.Conversation;
import com.xkamil.storage.Token;
import com.xkamil.storage.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/conversations")
public class ConversationsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Conversation> getAllUserConversations(@RequestAttribute("user") User user) throws Exception {

        return conversationRepository.findForUser(user.getId());

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Conversation getOrCreateIfNotExistsForUsers(
            @RequestAttribute("user") User user,
            @RequestBody List<String> usersIds
    ) throws Exception {

        usersIds.add(user.getId());
        List<Conversation> conversations = conversationRepository.findForUsersStrict(usersIds);

        if (conversations.size() == 0) {
            return conversationRepository.add(usersIds);
        }

        return conversations.get(0);
    }

    @RequestMapping(value = "/{conversation_id}/sentence", method = RequestMethod.POST)
    public Conversation addSentence(
            @RequestAttribute("user") User user,
            @PathVariable("conversation_id") String conversationId,
            @RequestParam("content") String content
    ) throws Exception {

        return conversationRepository.addSentence(conversationId, user.getId(), content);

    }
}