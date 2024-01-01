package com.openclassrooms.chatpoc.service;

import com.openclassrooms.chatpoc.dto.ChatRequest;
import com.openclassrooms.chatpoc.exception.NotFoundException;
import com.openclassrooms.chatpoc.model.Chat;
import com.openclassrooms.chatpoc.model.User;
import com.openclassrooms.chatpoc.repository.ChatRepository;
import com.openclassrooms.chatpoc.repository.UserRepository;

import io.jsonwebtoken.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public Chat save(Chat chat) {
        return this.chatRepository.save(chat);
    }

    public List<Chat> findAll() {
        return this.chatRepository.findAll();
    }

    public Optional<Chat> getById(Integer id) {
        return this.chatRepository.findById(id);
    }

    public Chat createChat(ChatRequest chatRequest) throws IOException {

        Chat newChat = new Chat();
        newChat.setSubject(chatRequest.getSubject());
        Timestamp now = Timestamp.from(Instant.now());
        newChat.setCreated_at(now);
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Récupére l'ID de l'utilisateur authentifié
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        newChat.setCreator(user);
    
        chatRepository.save(newChat);
    
        return newChat;
    }


}
