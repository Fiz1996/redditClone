package com.example.reddisclone.controller;

import com.example.reddisclone.dto.CommentsDto;
import com.example.reddisclone.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;
    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
    commentsService.save(commentsDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPosts(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsForPosts(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllComments( @PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentsService.getAllCommentsForUsers(userName));
    }
}
