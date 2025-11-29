package com.blogs.DTOs;

import com.blogs.Model.Comment;
import com.blogs.Model.Post;
import com.blogs.Model.User;

public class CommentMapper {
    public static CommentResponseDto toDto(Comment comment) {
        User user = comment.getUser();
        Post post = comment.getPost();

        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileImageUrl());
        PostDto postDto = new PostDto(post.getTitle(), post.getContent());
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                userDto,
                postDto
        );
    }
}
