package com.blogs.Controller;

import com.blogs.DTOs.PostRequestDto;
import com.blogs.DTOs.PostResponseDto;
import com.blogs.DTOs.UserDto;
import com.blogs.Service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "https://byteblogy.vercel.app/")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping("/createPost")
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostRequestDto postDto,
            Principal principal
    ) {
        String username = principal.getName();
        PostResponseDto response = service.createPost(postDto, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/viewAllPost")
    public ResponseEntity<List<PostResponseDto>> viewPosts(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(service.getAllPosts(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> viewPost(@PathVariable Integer id, @RequestParam String username){
        return ResponseEntity.ok(service.getPost(id, username));
    }

//    @PutMapping("updateLike/{id}")
//    public ResponseEntity<String> updateLike(@PathVariable Integer id, @RequestParam String username){
//        return ResponseEntity.ok(service.updateLike(id, username));
//    }

    @PutMapping("/toggleLike/{id}")
    public ResponseEntity<String> toggleLike(
            @PathVariable Integer id,
            @RequestParam String username
    ) {
        return ResponseEntity.ok(service.toggleLike(id, username));
    }

    @GetMapping("/likedUser/{id}")
    public ResponseEntity<List<UserDto>> getLikedUsers(@PathVariable Integer id){
        return ResponseEntity.ok(service.getLikedUsers(id));
    }

}
