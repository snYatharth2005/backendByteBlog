package com.blogs.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class Home{
    @GetMapping
    public ResponseEntity<String> home(){
        return new ResponseEntity<>("Welcome to home page", HttpStatus.OK);
    }
}
