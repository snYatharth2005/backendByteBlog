package com.blogs.DTOs;

import lombok.Data;

@Data
public class UserProfileDto {

    private Integer id;
    private String username;
    private String fullName;
    private Integer age;
    private String about;

    private String twitter;
    private String linkedin;
    private String github;
    private String leetcode;

    private String profileImageUrl;

    private Integer postsCount;
    private Integer followers;   // optional, set 0 for now
}

