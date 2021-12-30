package com.example.reddisclone.controller;

import com.example.reddisclone.dto.PostRequests;
import com.example.reddisclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postServcie;

//    @PostMapping
//    public void createPost(@RequestBody PostRequests postRequests) {
//        postServcie.save(postRequests);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

}
