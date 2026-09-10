package com.cremaplace.api.service;

import com.cremaplace.api.model.User;
import com.cremaplace.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(String username, String email, String password, String phoneNumber) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        long count = userRepository.count();
        String role = (count == 0) ? "ADMIN" : "USER";
        User user = new User(username, email, passwordEncoder.encode(password), phoneNumber, Collections.singletonList(role));
        userRepository.save(user);
    }
}
