package com.sampleProject.sampleProjectRestApi.Authentication;

import com.sampleProject.sampleProjectRestApi.Registeration.Registration;
import com.sampleProject.sampleProjectRestApi.login.LoginRequest;
import com.sampleProject.sampleProjectRestApi.user.User;
import com.sampleProject.sampleProjectRestApi.user.UserDTO;
import com.sampleProject.sampleProjectRestApi.user.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtConfig jwtConfig, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUserName(loginRequest.getUserName());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Key key = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .setSubject(user.getUserName())
                .claim("userId", user.getId())
                .claim("role",user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(key)
                .compact();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user",new UserDTO(user.getId(),user.getUserName(),user.getEmail(),user.getRole()));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Registration registerUser) {
        // Basic null or empty checks
        if (registerUser.getUserName() == null || registerUser.getUserName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required");
        }

        if (registerUser.getEmailAddress() == null || registerUser.getEmailAddress().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        if (!registerUser.getEmailAddress().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }

        if (registerUser.getPassword() == null || registerUser.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters");
        }

        if (!registerUser.getPassword().equals(registerUser.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        Optional<User> optionalUser = userRepository.findByUserName(registerUser.getUserName());
        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        Optional<User> existingEmail= userRepository.findByEmail(registerUser.getEmailAddress());
        if(existingEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Address already exists Please Log in ");
        }

        // Create the new user object
        User newUser = new User();
        newUser.setUserName(registerUser.getUserName());
        newUser.setEmail(registerUser.getEmailAddress());
        newUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        newUser.setRole("USER");
        newUser.setCreatedAt(new Date());
        System.out.println("Getting Role of registered person");
        System.out.println(newUser.getRole());

        userRepository.save(newUser);
        Key key = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .setSubject(newUser.getUserName())
                .claim("userId", newUser.getId())
                .claim("role",newUser.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(key)
                .compact();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user",new UserDTO(newUser.getId(),newUser.getUserName(),newUser.getEmail(),newUser.getRole()));

        return ResponseEntity.ok(response);

    }

}
