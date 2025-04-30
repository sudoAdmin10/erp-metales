package com.example.erpmetales.service;

import com.example.erpmetales.model.Users;
import com.example.erpmetales.dao.UserDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userRepository;
    private final PasswordEncoder passwordEncoder; // Usa la interfaz, no la implementación

    public UserService(UserDao userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users registerUser(Users user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            System.out.println("---------------");
            throw new RuntimeException("Email already in use");

        }

        // Asegúrate que el rol tenga el prefijo ROLE_
        if (!user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole());
            System.out.println("ROLE SIN PREFIJO");
        }

        System.out.print("PASSWORD: " + user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}