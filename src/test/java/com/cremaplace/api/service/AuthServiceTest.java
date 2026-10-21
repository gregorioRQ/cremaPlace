package com.cremaplace.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cremaplace.api.model.User;
import com.cremaplace.api.repository.UserRepository;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUserShouldSaveUserWithAdminRoleWhenFirstUser() {
        // Given
        String username = "admin";
        String email = "admin@example.com";
        String password = "password";
        String phoneNumber = "123456789";
        String encodedPassword = "encodedPassword";
        
        given(userRepository.count()).willReturn(0L); // No users yet
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
        given(passwordEncoder.encode(password)).willReturn(encodedPassword);

        // When
        authService.registerUser(username, email, password, phoneNumber);

        // Then
        // Verify that save was called with a User having ADMIN role
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getUsername()).isEqualTo(username);
        assertThat(capturedUser.getEmail()).isEqualTo(email);
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(capturedUser.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(capturedUser.getRoles()).containsExactly("ADMIN");
    }

    @Test
    void registerUserShouldSaveUserWithUserRoleWhenNotFirstUser() {
        // Given
        String username = "regularUser";
        String email = "user@example.com";
        String password = "password";
        String phoneNumber = "987654321";
        String encodedPassword = "encodedPassword";
        
        given(userRepository.count()).willReturn(5L); // Already have users
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
        given(passwordEncoder.encode(password)).willReturn(encodedPassword);

        // When
        authService.registerUser(username, email, password, phoneNumber);

        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getRoles()).containsExactly("USER");
    }

    @Test
    void registerUserShouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        String email = "existing@example.com";
        User existingUser = new User("existing", email, "password", "123456789", Collections.singletonList("USER"));
        given(userRepository.findByEmail(email)).willReturn(Optional.of(existingUser));

        // When & Then
        assertThatThrownBy(() -> authService.registerUser("username", email, "password", "phone"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email already exists");
        
        // Verify that save was never called
        verify(userRepository, never()).save(any());
    }
}