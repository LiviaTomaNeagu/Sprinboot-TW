package com.example.rest_api.controller;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OAuth2ErrorHandler {
    @ExceptionHandler(OAuth2AuthenticationException.class)
    public String handleOAuth2Error(OAuth2AuthenticationException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
