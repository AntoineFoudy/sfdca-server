package com.cloudstorage.sfdcaserver.controller;

import com.cloudstorage.sfdcaserver.crypto.KeyGeneratorService;
import com.cloudstorage.sfdcaserver.dtos.SignUpDto;
import com.cloudstorage.sfdcaserver.entities.User;
import com.cloudstorage.sfdcaserver.repositories.UserRepository;
import com.cloudstorage.sfdcaserver.service.PasswordHasher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user-signup")
public class SignUpController {

    private final UserRepository userRepository;

    private final PasswordHasher passwordHasher;

    private final KeyGeneratorService keyGeneratorService;

    public SignUpController(UserRepository userRepository, KeyGeneratorService keyGeneratorService, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.keyGeneratorService = keyGeneratorService;
        this.passwordHasher = passwordHasher;

    }

    @PostMapping
    public ResponseEntity<?> userSignUp(@RequestBody SignUpDto signUpDto) throws NoSuchAlgorithmException {
        // Check if email already exists
        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!");
        }

        // Create new User
        User user = new User();
        user.setEmail(signUpDto.getEmail());
        // Generate the hashed password and save only the hash
        user.setHashedPassword(passwordHasher.hash(signUpDto.getHashedPassword()));
        user.setRole(signUpDto.getRole());
        user.setSector(signUpDto.getSector());
        // Generate the RSA Key Pair
        List<String> rsaKeyPair = keyGeneratorService.rsaKeyGenerator();
        user.setPublicKey(rsaKeyPair.get(0));
        user.setPrivateKey(rsaKeyPair.get(1));

        // Save the user to the database
        userRepository.save(user);

        // Return status code 201 to indicate successful Sign up
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
