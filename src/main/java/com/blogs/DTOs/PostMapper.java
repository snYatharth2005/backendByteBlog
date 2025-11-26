package com.blogs.DTOs;

import com.blogs.Model.Post;
import com.blogs.Model.User;

public class PostMapper {

    public static PostResponseDto toDto(Post post, boolean likedByUser) {
        User user = post.getUser();

        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileImageUrl());

        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getLikeCount(),
                likedByUser,
                userDto
        );
    }
}
