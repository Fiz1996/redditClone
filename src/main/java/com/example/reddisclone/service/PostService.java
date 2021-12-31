package com.example.reddisclone.service;

import com.example.reddisclone.dto.PostRequests;
import com.example.reddisclone.dto.PostResponse;
import com.example.reddisclone.entity.Post;
import com.example.reddisclone.entity.Subreddit;
import com.example.reddisclone.entity.Users;
import com.example.reddisclone.exception.PostNotFoundException;
import com.example.reddisclone.exception.SubredditNotFountException;
import com.example.reddisclone.mapper.PostMapper;
import com.example.reddisclone.repository.PostRepository;
import com.example.reddisclone.repository.SubredditRepository;
import com.example.reddisclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository usersRepository;


    public void save(PostRequests postRequests) {
        Subreddit subreddit = subredditRepository.findByName(postRequests.getSubredditName())
                .orElseThrow(() -> new SubredditNotFountException(postRequests.getSubredditName()));
        postRepository.save(postMapper.map(postRequests, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(()-> new SubredditNotFountException(subredditId.toString()));
        List<Post> posts = postRepository.findBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional
    public List<PostResponse> getPostByUsername(String username) {
        Users user= usersRepository.findByUsername(username)
                .orElseThrow( ()-> new UsernameNotFoundException(username));
        return postRepository.findByUsers(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

}
