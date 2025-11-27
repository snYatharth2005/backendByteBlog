package com.blogs.Controller;

import com.blogs.DTOs.ProfileUpdateDto;
import com.blogs.DTOs.UserDto;
import com.blogs.DTOs.UserProfileDto;
import com.blogs.Service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "https://byteblogy.vercel.app/")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service){
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile(Principal principal) {

        String username = principal.getName();  // fetched from JWT
        UserProfileDto dto = service.getLoggedInProfile(username);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfile(@RequestBody ProfileUpdateDto profile, Principal principal){
        String username = principal.getName();
        UserDto updated = service.updateProfile(profile, username);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile file, Principal principal){
        String username = principal.getName();
        String imageUrl = service.uploadProfileImage(username, file);

        return ResponseEntity.ok(imageUrl);
    }


}
