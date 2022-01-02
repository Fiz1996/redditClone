package com.example.reddisclone.service;

import com.example.reddisclone.dto.VoteDto;
import com.example.reddisclone.entity.Post;
import com.example.reddisclone.entity.Vote;
import com.example.reddisclone.entity.VoteType;
import com.example.reddisclone.exception.PostNotFoundException;
import com.example.reddisclone.exception.RedditCloneExcption;
import com.example.reddisclone.repository.PostRepository;
import com.example.reddisclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found exception with Id" + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findByPostAndUsersOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new RedditCloneExcption("You have already" + voteDto.getVoteType() + "'d for this post");
        }

        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .users(authService.getCurrentUser())
                .build();
    }
}
