package com.openclassrooms.chatpoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatpoc.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    Boolean existsByEmail(String email); 

    @Query("SELECT u.roles FROM User u WHERE u.email = ?1")
    List<String> findRolesByEmail(String email);

}
