package com.blogs.Service;

import com.blogs.DTOs.CommentMapper;
import com.blogs.DTOs.CommentRequestDto;
import com.blogs.DTOs.CommentResponseDto;
import com.blogs.DTOs.PostMapper;
import com.blogs.Model.Comment;
import com.blogs.Model.Post;
import com.blogs.Model.User;
import com.blogs.Repository.CommentRepository;
import com.blogs.Repository.PostRepository;
import com.blogs.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public CommentService(CommentRepository commmentRepo, PostRepository postRepo, UserRepository userRepo){
        this.commentRepo = commmentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<CommentResponseDto> createPostComment(CommentRequestDto newComment, Integer postId, String username) {
        Post post = postRepo.findById(postId).orElse(null);
        User user = userRepo.findByUsername(username).orElse(null);

        Comment comment = new Comment();
        comment.setContent(newComment.getContent());
        comment.setPost(post);
        comment.setUser(user);

        comment = commentRepo.save(comment);

        return new ResponseEntity<>(CommentMapper.toDto(comment), HttpStatus.CREATED);
    }

    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(Integer postId) {
        Post post = postRepo.findById(postId).orElse(null);

        assert post != null;

        List<CommentResponseDto> resComments = post.getComments()
                .stream()
                .map(CommentMapper::toDto)
                .toList();

        return ResponseEntity.ok(resComments);
    }
}
