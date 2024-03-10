package com.openclassrooms.chatpoc.controller;

import com.openclassrooms.chatpoc.dto.MessageRequest;
import com.openclassrooms.chatpoc.exception.NotFoundException;
import com.openclassrooms.chatpoc.model.Message;
import com.openclassrooms.chatpoc.model.User;
import com.openclassrooms.chatpoc.response.MessageResponse;
import com.openclassrooms.chatpoc.response.MessagesResponse;
import com.openclassrooms.chatpoc.security.JwtTokenProvider;
import com.openclassrooms.chatpoc.service.MessageService;

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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/message")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping(path = "/add")
      public ResponseEntity<MessageResponse> createMessage(@RequestBody MessageRequest messageRequest) {
    
            try {
                messageService.createMessage(messageRequest);
  
                MessageResponse messageResponse = new MessageResponse("Message created!");
  
                return ResponseEntity.ok(messageResponse);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    }

    @GetMapping(path = "/list/{chatId}")
    public ResponseEntity<MessagesResponse> getMessagesByChat(@PathVariable Integer chatId) {
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        MessagesResponse messagesResponse = new MessagesResponse(messages);
        return ResponseEntity.ok(messagesResponse);
    }


}


 

