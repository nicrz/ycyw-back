package com.openclassrooms.chatpoc.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NonNull
  @Size(max = 50)
  @Column(name = "firstname")
  private String firstname;

  @NonNull
  @Size(max = 50)
  @Column(name = "lastname")
  private String lastname;

  @NonNull
  @Size(max = 50)
  @Email
  private String email;

  @NonNull
  @Size(min = 8)
  @Column(name = "password")
  private String password;

  @NonNull
  @Column(name = "birthdate", updatable = false)
  private Timestamp birthdate;

  @NonNull
  @Size(max = 100)
  @Column(name = "address")
  private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @JoinTable(name = "user_roles", 
               joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles", nullable = false)
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

  @Override
  public String getUsername() {
      return email; // Utilisez la propriété appropriée pour le nom d'utilisateur
  }

  @Override
  public boolean isAccountNonExpired() {
      return true; // Votre logique pour vérifier si le compte n'a pas expiré
  }

  @Override
  public boolean isAccountNonLocked() {
      return true; // Votre logique pour vérifier si le compte n'est pas verrouillé
  }

  @Override
  public boolean isCredentialsNonExpired() {
      return true; // Votre logique pour vérifier si les informations d'identification ne sont pas expirées
  }

  @Override
  public boolean isEnabled() {
      return true; // Votre logique pour vérifier si le compte est activé
  }


}
