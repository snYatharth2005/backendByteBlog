package com.blogs.Controller;

import com.blogs.DTOs.CommentRequestDto;
import com.blogs.DTOs.CommentResponseDto;
import com.blogs.Service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin("https://byteblogy.vercel.app/")
//@CrossOrigin("http://localhost:5173/")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service){
        this.service = service;
    }

    @PostMapping("/postComment/{id}")
    public ResponseEntity<CommentResponseDto> postComment(@RequestBody CommentRequestDto newComment, @PathVariable Integer id, @RequestParam String username){
        return service.createPostComment(newComment, id, username);
    }

    @GetMapping("/getComments/{id}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Integer id){
        return service.getCommentsByPostId(id);
    }
}
