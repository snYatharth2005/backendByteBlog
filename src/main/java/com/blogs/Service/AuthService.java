package com.blogs.Service;

import com.blogs.Auth.JWTService;
import com.blogs.DTOs.LoginRequest;
import com.blogs.DTOs.RegisterRequest;
import com.blogs.Model.User;
import com.blogs.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository repo;
    private AuthenticationManager authManager;
    private JWTService jwtService;

    AuthService(UserRepository repo, AuthenticationManager authManager, JWTService jwtService){
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<String> addUser(RegisterRequest req) {
        if(repo.findByEmail(req.email()).isPresent() || repo.findByUsername(req.username()).isPresent())
        {
            return new ResponseEntity<>("User already exist", HttpStatus.OK);
        }

        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .build();

        repo.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    public String loginUser(LoginRequest req) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(req.username());
        }
        throw new RuntimeException("Authentication failed");
    }
}
