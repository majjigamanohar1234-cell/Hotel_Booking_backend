package com.stayease.auth;

import com.stayease.user.User;
import com.stayease.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required.");
        }
        if (request.getPassword() == null || request.getPassword().trim().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 4 characters.");
        }
        String cleanEmail = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(cleanEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists.");
        }

        String name = (request.getName() != null && !request.getName().trim().isEmpty())
                ? request.getName().trim()
                : cleanEmail.split("@")[0];

        String role = (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN"))
                ? "ROLE_ADMIN"
                : "ROLE_USER";

        String hashedPassword = passwordEncoder.encode(request.getPassword().trim());
        User user = new User(name, cleanEmail, hashedPassword, role);
        User saved = userRepository.save(user);

        return new AuthResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole(), "User registered successfully");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required.");
        }

        String cleanEmail = request.getEmail().trim().toLowerCase();
        User user = userRepository.findByEmailIgnoreCase(cleanEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword().trim(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password.");
        }

        return new AuthResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), "Login successful");
    }

    @GetMapping("/me")
    public AuthResponse me(@RequestParam String email) {
        User user = userRepository.findByEmailIgnoreCase(email.trim().toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        return new AuthResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), "User profile fetched");
    }
}
