package org.example.clickstack.Repository;

import org.example.clickstack.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
