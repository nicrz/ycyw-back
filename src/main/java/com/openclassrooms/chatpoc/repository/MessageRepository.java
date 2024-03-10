package com.openclassrooms.chatpoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatpoc.model.Message;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByChatId(Integer chatId);

}

