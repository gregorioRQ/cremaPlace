package com.cremaplace.api.service;

import com.cremaplace.api.model.User;
import com.cremaplace.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> searchUsersByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return getAllUsers();
        }
        return userRepository.findByUsernameContainingIgnoreCase(username.trim());
    }

    public void updateUserRole(String userId, String newRole) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setRoles(java.util.Collections.singletonList(newRole));
            userRepository.save(user);
        });
    }

    public void unlockUserAccount(String userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setFailedLoginAttempts(0);
            userRepository.save(user);
        });
    }
}
