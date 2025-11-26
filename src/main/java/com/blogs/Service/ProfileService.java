package com.blogs.Service;

import com.blogs.DTOs.ProfileUpdateDto;
import com.blogs.DTOs.UserDto;
import com.blogs.DTOs.UserMapper;
import com.blogs.DTOs.UserProfileDto;
import com.blogs.Model.User;
import com.blogs.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProfileService {
    private final UserRepository repo;

    public ProfileService(UserRepository repo){
        this.repo = repo;
    }


    public UserDto updateProfile(ProfileUpdateDto profile, String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not found"));


        user.setFullName(profile.getFullName());
        user.setAge(profile.getAge());
        user.setAbout(profile.getAbout());
        user.setTwitter(profile.getTwitter());
        user.setLinkedin(profile.getLinkedin());
        user.setGithub(profile.getGithub());
        user.setLeetcode(profile.getLeetcode());

        repo.save(user);

        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }

    public String uploadProfileImage(String username, MultipartFile file) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String uploadDir = "uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs(); // create folder if missing

        String fileName = username + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);

        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file");
        }

        user.setProfileImageUrl("http://localhost:8080/uploads/" + fileName);
        repo.save(user);

        return user.getProfileImageUrl();
    }

    public UserProfileDto getLoggedInProfile(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toProfileDto(user);
    }

}
