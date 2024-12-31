package com.example.rest_api.controller;

import com.example.rest_api.database.primary.model.UserEntity;
import com.example.rest_api.service.UserService;
import com.example.rest_api.service.UserValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private UserService userService;
    private UserValidatorService userValidatorService;
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    public RegisterController(UserService userService, UserValidatorService userValidatorService) {
        this.userService = userService;
        this.userValidatorService = userValidatorService;
    }

    @GetMapping()
    public String loadRegisterPage(Model model){
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping()
    public String register(@ModelAttribute("user") UserEntity user, BindingResult bindingResult){
        userValidatorService.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "register";

        if (userService.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered!");
        }

        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setIsOAuthAccount(false); // Classic registration
        userService.save(user);

        return "redirect:/login";
    }
}
