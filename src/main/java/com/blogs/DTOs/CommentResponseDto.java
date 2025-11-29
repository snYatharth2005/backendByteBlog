package com.blogs.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentResponseDto {
    private Integer id;
    private String content;
    private LocalDateTime createdAt;
    private UserDto user;
    private PostDto post;
}
