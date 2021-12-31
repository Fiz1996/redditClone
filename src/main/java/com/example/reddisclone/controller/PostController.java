package com.example.reddisclone.controller;

import com.example.reddisclone.dto.PostRequests;
import com.example.reddisclone.dto.PostResponse;
import com.example.reddisclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequests postRequests) {
        postService.save(postRequests);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return  ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return  ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    // getPostBySubreddit

    // getPostByUsername

}
