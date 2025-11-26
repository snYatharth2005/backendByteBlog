package com.blogs.DTOs;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String username;
    private String email;

    private String fullName;
    private Integer age;
    private String about;

    private String twitter;
    private String linkedin;
    private String github;

    private String leetcode;
    private String profileImageUrl;

    public UserDto(Integer id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public UserDto(Integer id, String username, String email, String profileImageUrl){
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }
}
