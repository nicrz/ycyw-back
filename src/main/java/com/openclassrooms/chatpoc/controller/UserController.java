package com.openclassrooms.chatpoc.controller;

import com.openclassrooms.chatpoc.dto.LoginRequest;
import com.openclassrooms.chatpoc.dto.RegisterRequest;
import com.openclassrooms.chatpoc.exception.NotFoundException;
import com.openclassrooms.chatpoc.model.User;
import com.openclassrooms.chatpoc.response.AuthSuccess;
import com.openclassrooms.chatpoc.security.JwtTokenProvider;
import com.openclassrooms.chatpoc.service.UserService;

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
@RequestMapping(path = "/api/auth")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Autowired
  private AuthenticationProvider authenticationProvider;
  
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  

  @PostMapping("/login")
  public ResponseEntity<AuthSuccess> login(@RequestBody LoginRequest loginRequest) {
    try {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Appelle la méthode du service pour vérifier les informations d'identification
        Authentication authentication = userService.login(email, password);

        // Génère le token JWT
        String token = jwtTokenProvider.generateToken(authentication);

        // Crée l'objet AuthSuccess avec le token
        AuthSuccess authSuccess = new AuthSuccess(token);

        // Retourne l'objet AuthSuccess dans la réponse
        return ResponseEntity.ok(authSuccess);
    } catch (AuthenticationException e) {
        // Retourne une réponse 401 Unauthorized en cas d'erreur
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

  @PostMapping(path = "/register")
  public ResponseEntity<AuthSuccess> addNewUser(@RequestBody RegisterRequest registerRequest) {
    try {
        // Appelle la méthode du service pour ajouter un nouvel utilisateur
        User newUser = userService.addNewUser(registerRequest);

        // Authentifie l'utilisateur après son enregistrement
        Authentication authentication = authenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(newUser.getEmail(), registerRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Génère le token JWT
        String token = jwtTokenProvider.generateToken(authentication);

        // Crée l'objet AuthSuccess avec le token
        AuthSuccess authSuccess = new AuthSuccess(token);

        // Retourne l'objet AuthSuccess dans la réponse
        return ResponseEntity.ok(authSuccess);
    } catch (Exception e) {
        // Retourne une réponse 400 s'il y a une erreur lors de l'ajout
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}


}
