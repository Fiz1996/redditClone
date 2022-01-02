package com.example.reddisclone.mapper;

import com.example.reddisclone.dto.CommentsDto;
import com.example.reddisclone.entity.Comment;
import com.example.reddisclone.entity.Post;
import com.example.reddisclone.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target="id" ,ignore = true)
    @Mapping(target = "text" ,source = "commentsDto.text")
    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "post",source = "post")
    Comment map(CommentsDto commentsDto, Post post, Users user);

    @Mapping(target = "postId",expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username",expression = "java(comment.getUsers().getUsername())")
    CommentsDto mapToDto(Comment comment);
}
