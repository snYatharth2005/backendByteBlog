package com.blogs.DTOs;

import com.blogs.DTOs.UserProfileDto;
import com.blogs.Model.User;

public class UserMapper {

    public static UserProfileDto toProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();

        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setUsername(user.getUsername());
        dto.setAge(user.getAge());
        dto.setAbout(user.getAbout());

        dto.setTwitter(user.getTwitter());
        dto.setLinkedin(user.getLinkedin());
        dto.setGithub(user.getGithub());
        dto.setLeetcode(user.getLeetcode());

        dto.setProfileImageUrl(user.getProfileImageUrl());

        dto.setPostsCount(user.getPosts() != null ? user.getPosts().size() : 0);
        dto.setFollowers(0); // for now

        return dto;
    }
}

