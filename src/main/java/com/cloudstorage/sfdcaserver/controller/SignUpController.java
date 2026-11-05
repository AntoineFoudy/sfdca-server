package com.cloudstorage.sfdcaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user-signup")
public class SignUpController {

    public ResponseEntity<?> userSignUp(@RequestBody SignUpDto signUpDto) {
        // DTO logic to be added here

        // Check if email already

        // Get password hash

        // Generate new User + Save info + Save password hash

    }



}
