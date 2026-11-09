package com.cloudstorage.sfdcaserver.repositories;

import com.cloudstorage.sfdcaserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // For Login, Getting user's public/private keys, and Getting user's role/sector
    Optional<User> findByEmail(String email);

    // For Registration / Checking email uniqueness
    boolean existsByEmail(String email);
}
