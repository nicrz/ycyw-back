package com.openclassrooms.chatpoc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "message")
@EntityListeners(AuditingEntityListener.class)
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer id;

  @Size(max = 2000)
  @Column(nullable = false)
  @Getter @Setter private String content;

  @OneToOne
  @JoinColumn(name = "sender", referencedColumnName = "id")
  @Getter @Setter private User sender;

  @ManyToOne
  @JoinColumn(name = "chat_id", nullable = false)
  @Getter @Setter private Chat chat;

}

