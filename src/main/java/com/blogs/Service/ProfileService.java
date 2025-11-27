package com.blogs.Service;

import com.blogs.DTOs.ProfileUpdateDto;
import com.blogs.DTOs.UserDto;
import com.blogs.DTOs.UserMapper;
import com.blogs.DTOs.UserProfileDto;
import com.blogs.Model.User;
import com.blogs.Repository.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class ProfileService {

    private final Cloudinary cloudinary;
    private final UserRepository repo;

    public ProfileService(UserRepository repo, Cloudinary cloudinary){
        this.repo = repo;
        this.cloudinary = cloudinary;
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

        try {
            // Upload to Cloudinary with folder
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "uploads/",
                            "public_id", username + "_profile",
                            "overwrite", true,
                            "resource_type", "image"
                    )
            );

            // Get secure URL
            String imageUrl = uploadResult.get("secure_url").toString();

            // Save in DB
            user.setProfileImageUrl(imageUrl);
            repo.save(user);

            return imageUrl;

        } catch (IOException e) {
            throw new RuntimeException("Cloudinary upload failed: " + e.getMessage());
        }
    }

    public UserProfileDto getLoggedInProfile(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toProfileDto(user);
    }

}
