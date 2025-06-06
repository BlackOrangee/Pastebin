package com.pastebin.pastebin.repository;

import com.pastebin.pastebin.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {


    Optional<User> findByUsername(@NotBlank(message = "Username is required") String username);
}
