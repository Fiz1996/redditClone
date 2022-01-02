package com.example.reddisclone.mapper;

import com.example.reddisclone.dto.PostRequests;
import com.example.reddisclone.dto.PostResponse;
import com.example.reddisclone.entity.Post;
import com.example.reddisclone.entity.Subreddit;
import com.example.reddisclone.entity.Users;
import com.example.reddisclone.repository.CommentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount" ,constant = "0")
    @Mapping(target = "subreddit", source = "subreddit")
   public abstract   Post map(PostRequests postRequest, Subreddit subreddit, Users user);

    @Mapping(target = "id",source = "postId")
    @Mapping(target = "subredditName",source = "subreddit.name")
    @Mapping(target = "username", source = "users.username")
    @Mapping(target = "commentCount" ,expression = "java(commentCount(post))")
//    @Mapping(target = "duration" , expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post ){return commentRepository.findByPost(post).size(); }



}
