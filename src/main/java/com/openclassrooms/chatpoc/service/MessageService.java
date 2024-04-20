package com.openclassrooms.chatpoc.service;

import com.openclassrooms.chatpoc.dto.MessageRequest;
import com.openclassrooms.chatpoc.exception.NotFoundException;
import com.openclassrooms.chatpoc.model.Chat;
import com.openclassrooms.chatpoc.model.Message;
import com.openclassrooms.chatpoc.model.User;
import com.openclassrooms.chatpoc.repository.ChatRepository;
import com.openclassrooms.chatpoc.repository.MessageRepository;
import com.openclassrooms.chatpoc.repository.UserRepository;

import io.jsonwebtoken.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public Message save(Message msg) {
        return this.messageRepository.save(msg);
    }

    public List<Message> findAll() {
        return this.messageRepository.findAll();
    }

    public Optional<Message> getById(Integer id) {
        return this.messageRepository.findById(id);
    }

    public Message createMessage(MessageRequest messageRequest) throws IOException {

        Message newMessage = new Message();
        newMessage.setContent(messageRequest.getContent());
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Récupére l'ID de l'utilisateur authentifié
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        newMessage.setSender(user);
        newMessage.setSenderName(user.getFirstname() + ' ' + user.getLastname());

        // Récupère l'ID du chat depuis la requête
        Integer chatId = messageRequest.getChat();
        if (chatId != null) {
            Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat not found"));
            newMessage.setChat(chat);
        }
    
        messageRepository.save(newMessage);
    
        return newMessage;
    }

    public List<Message> getMessagesByChatId(Integer chatId) {
        return messageRepository.findByChatId(chatId);
    }


}

