package com.example.reddisclone.mapper;

import com.example.reddisclone.dto.PostRequests;
import com.example.reddisclone.dto.PostResponse;
import com.example.reddisclone.entity.Post;
import com.example.reddisclone.entity.Subreddit;
import com.example.reddisclone.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequests postRequest, Subreddit subreddit, Users user);

    @Mapping(target = "id",source = "postId")
    @Mapping(target = "subredditName",source = "subreddit.name")
    @Mapping(target = "username", source = "users.username")
    PostResponse mapToDto(Post post);

}
