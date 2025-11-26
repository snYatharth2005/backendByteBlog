package com.blogs.DTOs;

import lombok.Data;

@Data
public class ProfileUpdateDto {
    private String fullName;
    private Integer age;
    private String about;
    private String twitter;
    private String linkedin;
    private String github;
    private String leetcode;
}

