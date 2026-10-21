package com.cremaplace.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cremaplace.api.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void loginPageShouldReturnLoginView() {
        String viewName = authController.login();
        assertThat(viewName).isEqualTo("login");
    }

    @Test
    void registerPageShouldReturnRegisterView() {
        String viewName = authController.register();
        assertThat(viewName).isEqualTo("register");
    }

    @Test
    void registerUserShouldRedirectToLogin() {
        // When
        String redirectUrl = authController.registerUser("username", "email@example.com", "password", "phone");
        
        // Then
        assertThat(redirectUrl).isEqualTo("redirect:/login");
        // Verify that authService.registerUser was called with the correct parameters
        verify(authService).registerUser("username", "email@example.com", "password", "phone");
    }
}