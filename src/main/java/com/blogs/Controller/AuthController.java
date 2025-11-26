package com.blogs.Controller;

import com.blogs.DTOs.AuthResponse;
import com.blogs.DTOs.LoginRequest;
import com.blogs.DTOs.RegisterRequest;
import com.blogs.Model.User;
import com.blogs.Repository.UserRepository;
import com.blogs.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://byteblogy.netlify.app/")
public class AuthController {
    private AuthService service;

    AuthController(AuthService service, UserRepository repo){
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest req){
        return service.addUser(req);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest req){
        String token = service.loginUser(req);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
