package com.sampleProject.sampleProjectRestApi.Authentication;

import com.sampleProject.sampleProjectRestApi.Authentication.blacklistedToken.BlackListedRepository;
import com.sampleProject.sampleProjectRestApi.Authentication.blacklistedToken.BlacklistedToken;
import com.sampleProject.sampleProjectRestApi.Registeration.Registration;
import com.sampleProject.sampleProjectRestApi.login.LoginRequest;
import com.sampleProject.sampleProjectRestApi.user.User;
import com.sampleProject.sampleProjectRestApi.user.UserDTO;
import com.sampleProject.sampleProjectRestApi.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlackListedRepository blacklistedTokenRepository;

    public AuthController(UserRepository userRepository, JwtConfig jwtConfig, PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository, BlackListedRepository blacklistedTokenRepository) {
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByUserName(loginRequest.getUserName());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Key key = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());

        // ACCESS TOKEN
        String token = Jwts.builder()
                .setSubject(user.getUserName())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration())) // e.g., 15 mins
                .signWith(key)
                .compact();

        // REFRESH TOKEN (longer lifespan, e.g., 7 days)
        String refreshToken = Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000))) // 7 days
                .signWith(key)
                .compact();

        // You can store refresh token in DB here (optional)
        // Save refresh token in DB
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken); // the string you generated
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setExpiryDate(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000));
        refreshTokenRepository.save(refreshTokenEntity);

        // Set refresh token as HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true) // true in production (HTTPS)
                .path("/api/refresh-token")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("LAX")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("user", new UserDTO(user.getId(), user.getUserName(), user.getEmail(), user.getRole()));
        return ResponseEntity.ok(responseBody);
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

        Optional<User> existingEmail = userRepository.findByEmail(registerUser.getEmailAddress());
        if (existingEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Address already exists. Please log in.");
        }

        // Create new user
        User newUser = new User();
        newUser.setUserName(registerUser.getUserName());
        newUser.setEmail(registerUser.getEmailAddress());
        newUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        newUser.setRole("USER");
        newUser.setCreatedAt(registerUser.getCreatedAt());

        userRepository.save(newUser);

        // Create Access and Refresh Tokens
        Key key = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .setSubject(newUser.getUserName())
                .claim("userId", newUser.getId())
                .claim("role", newUser.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(key)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(newUser.getUserName())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration()))
                .signWith(key)
                .compact();

        // Save refresh token in DB
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUser(newUser);
        refreshTokenEntity.setExpiryDate(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // 7 days
        refreshTokenRepository.save(refreshTokenEntity);

        // Prepare response body
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", new UserDTO(newUser.getId(), newUser.getUserName(), newUser.getEmail(), newUser.getRole()));

        // Set HttpOnly cookie for refresh token
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false) // true if using HTTPS
                .path("/") // Important: make the cookie available on all routes
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .sameSite("Lax") // or "Strict" depending on use case
                .build();

        // Return with cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }


    @PostMapping("/refresh-token")
//        in talend api tester, make sure you do this.
//        Content-Type: application/json (optional, if you're sending/expecting JSON)
//        Cookie: refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
    public ResponseEntity<?> refreshToken(@CookieValue(value = "refresh_token", required = false) String refreshToken) {
        System.out.println("Refresh token from cookie: " + refreshToken);
        if (refreshToken == null) {
            System.out.println("token is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token missing");
        }

        Optional<RefreshToken> savedTokenOpt = refreshTokenRepository.findByToken(refreshToken);
        if (savedTokenOpt.isEmpty()) {
            System.out.println("Token is empty");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found or revoked");
        }

        RefreshToken savedToken = savedTokenOpt.get();
        System.out.println("saved token " + savedToken);
        if (savedToken.getExpiryDate().before(new Date())) {
            System.out.println("Token date is expired..");
            refreshTokenRepository.delete(savedToken); // cleanup expired token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        }

        try {
            Key key = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String userName = claims.getSubject();
            Optional<User> optionalUser = userRepository.findByUserName(userName);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            User user = optionalUser.get();

            // New Access Token
            String newAccessToken = Jwts.builder()
                    .setSubject(user.getUserName())
                    .claim("userId", user.getId())
                    .claim("role", user.getRole())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                    .signWith(key)
                    .compact();

            Map<String, Object> response = new HashMap<>();
            response.put("token", newAccessToken);
            response.put("user", new UserDTO(user.getId(), user.getUserName(), user.getEmail(), user.getRole()));
            return ResponseEntity.ok(response);

        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        String authHeader = request.getHeader("Authorization");
        String refreshToken = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        System.out.println("=== Logout endpoint hit ===");
        System.out.println("Auth header: " + authHeader);
        System.out.println("Refresh token: " + refreshToken);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
            BlacklistedToken tokenEntity = new BlacklistedToken();
            tokenEntity.setToken(accessToken);
            blacklistedTokenRepository.save(tokenEntity);
        }

        if (refreshToken != null) {
            refreshTokenRepository.findByToken(refreshToken)
                    .ifPresent(refreshTokenRepository::delete);
        }

        ResponseCookie clearCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(false) // Use true only in HTTPS
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                .body("Logged out successfully");
    }


}
