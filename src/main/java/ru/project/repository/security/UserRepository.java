package ru.project.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.model.security.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
