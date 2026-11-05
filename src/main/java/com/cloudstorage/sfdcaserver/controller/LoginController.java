package com.cloudstorage.sfdcaserver.controller;

import com.cloudstorage.sfdcaserver.dtos.LoginDto;
import com.cloudstorage.sfdcaserver.dtos.RequestLoginDto;
import com.cloudstorage.sfdcaserver.entities.User;
import com.cloudstorage.sfdcaserver.repositories.UserRepository;
import com.cloudstorage.sfdcaserver.service.PasswordHasher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/login")
public class LoginController {

    private final UserRepository userRepository;

    public final PasswordHasher passwordHasher;

    public LoginController(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @PostMapping
    public ResponseEntity<?> userLogin(@RequestBody RequestLoginDto requestLoginDto) {
        // Check if user exists
        Optional<User> user = userRepository.findByEmail(requestLoginDto.getEmail());
        if(user.isPresent()) {
            // check if password is correct
            if(passwordHasher.verify(requestLoginDto.getPassword(), user.get().getHashedPassword())) {
                // if password is correct
                LoginDto loginDto = new LoginDto();
                loginDto.setUserId(user.get().getUserId());
                loginDto.setPublicKey(user.get().getPublicKey());
                loginDto.setPrivateKey(user.get().getPrivateKey());

                // Return User ID and RSA Key Pair to Client
                return ResponseEntity.ok(loginDto);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
