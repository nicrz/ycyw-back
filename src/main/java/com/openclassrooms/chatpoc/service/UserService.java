package com.openclassrooms.chatpoc.service;

import com.openclassrooms.chatpoc.dto.RegisterRequest;
import com.openclassrooms.chatpoc.exception.NotFoundException;
import com.openclassrooms.chatpoc.model.User;
import com.openclassrooms.chatpoc.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Integer id) {
        // integer à la base
        return userRepository.findById(id);
    }

    public Authentication login(String email, String password) {
        // Vérification des informations d'identification de l'utilisateur
        Authentication authentication = authenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public User addNewUser(RegisterRequest registerRequest) {
        // Utilisez les méthodes de RegisterRequest pour extraire les informations nécessaires
        String firstname = registerRequest.getFirstname();
        String lastname = registerRequest.getLastname();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        Timestamp birthdate = registerRequest.getBirthdate();
        String address = registerRequest.getAddress();

        // Assurez-vous de valider les données au besoin

        // Créez un nouvel utilisateur
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setBirthdate(birthdate);
        user.setAddress(address);

        List<String> roles = Arrays.asList("ROLE_USER"); // Vous pouvez ajouter d'autres rôles au besoin
        user.setRoles(roles);


        // Enregistrez l'utilisateur dans le référentiel (repository)
        return userRepository.save(user);
    }



}
