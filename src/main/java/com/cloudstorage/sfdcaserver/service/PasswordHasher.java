package com.cloudstorage.sfdcaserver.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    // Spring Security's built-in encoder
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 1. Takes plain string, returns hash
    public String hash(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }
        return encoder.encode(plaintext);
    }

    // 2. Checks if a plain password matches a hash
    public boolean verify(String plaintext, String hash) {
        if (plaintext == null || hash == null) return false;
        return encoder.matches(plaintext, hash);
    }
}
