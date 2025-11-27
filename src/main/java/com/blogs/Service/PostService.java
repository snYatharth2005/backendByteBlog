package com.blogs.Service;

import com.blogs.DTOs.*;
import com.blogs.Model.Post;
import com.blogs.Model.PostLiked;
import com.blogs.Model.User;
import com.blogs.Repository.PostLikeRepository;
import com.blogs.Repository.PostRepository;
import com.blogs.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private PostLikeRepository postLikeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public PostResponseDto createPost(PostRequestDto dto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setUser(user);

        post = postRepository.save(post);

        return PostMapper.toDto(post, false);//false because post has been just created and no one yet liked it
    }

    public List<PostResponseDto> getAllPosts(String username) {

        User currentUser = userRepository.findByUsername(username).orElse(null);

        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> {
                    boolean liked = currentUser != null &&
                            postLikeRepository.existsByUserAndPost(currentUser, post);

                    return PostMapper.toDto(post, liked);
                })
                .toList();
    }


    public PostResponseDto getPost(Integer id, String username) {
        User currUser = userRepository.findByUsername(username).orElse(null);

        return postRepository.findById(id)
                .map(post -> {
                    boolean liked = currUser != null && postLikeRepository.existsByUserAndPost(currUser, post);
                    return PostMapper.toDto(post, liked);
                })
                .orElseThrow(() -> new RuntimeException("Post Not Found with id " + id));
    }

//    public String updateLike(Integer id) {
//        Post post = postRepository.findById(id).get();
//        post.setLikeCount(post.getLikeCount()+1);
//        postRepository.save(post);
//        return "success";
//    }

    @Transactional
    public String toggleLike(Integer postId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean alreadyLiked = postLikeRepository.existsByUserAndPost(user, post);

        if (alreadyLiked) {
            // remove like
            postLikeRepository.deleteByUserAndPost(user, post);
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
            return "unliked";
        } else {
            // add like
            PostLiked like = PostLiked.builder()
                    .user(user)
                    .post(post)
                    .build();
            postLikeRepository.save(like);

            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);
            return "liked";
        }
    }

    public List<UserDto> getLikedUsers(Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        assert post != null;
        List<PostLiked> postLiked = post.getLikes();
        List<UserDto> result = new ArrayList<>();

        for(PostLiked postL: postLiked){
            UserDto newUser = new UserDto(postL.getUser().getId(), postL.getUser().getUsername(), postL.getUser().getEmail(), postL.getUser().getProfileImageUrl());
            result.add(newUser);
        }
        return result;
    }
}
