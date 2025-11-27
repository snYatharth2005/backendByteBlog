package com.blogs.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
//@CrossOrigin(origins = "https://byteblogy.vercel.app/")
@CrossOrigin(origins = "http://localhost:5173/")
public class Home{
    @GetMapping
    public ResponseEntity<String> home(){
        return new ResponseEntity<>("Welcome to home page", HttpStatus.OK);
    }
}
