package com.openclassrooms.chatpoc.controller;

import com.openclassrooms.chatpoc.dto.ChatRequest;
import com.openclassrooms.chatpoc.exception.NotFoundException;
import com.openclassrooms.chatpoc.model.User;
import com.openclassrooms.chatpoc.response.ChatResponse;
import com.openclassrooms.chatpoc.security.JwtTokenProvider;
import com.openclassrooms.chatpoc.service.ChatService;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/chat")
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping(path = "/add")
      public ResponseEntity<ChatResponse> createChat(@RequestBody ChatRequest chatRequest) {
    
            try {
                chatService.createChat(chatRequest);
  
                ChatResponse chatResponse = new ChatResponse("Chat created!");
  
                return ResponseEntity.ok(chatResponse);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    }


}


 
