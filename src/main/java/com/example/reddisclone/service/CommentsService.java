package com.example.reddisclone.service;

import com.example.reddisclone.dto.CommentsDto;
import com.example.reddisclone.entity.Comment;
import com.example.reddisclone.entity.NotificationEmail;
import com.example.reddisclone.entity.Post;
import com.example.reddisclone.entity.Users;
import com.example.reddisclone.exception.PostNotFoundException;
import com.example.reddisclone.mapper.CommentMapper;
import com.example.reddisclone.repository.CommentRepository;
import com.example.reddisclone.repository.PostRepository;
import com.example.reddisclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentsService  {
    private final static String  Post_Url="";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()-> new PostNotFoundException(commentsDto.getPostId().toString()));
         Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
        String message = mailContentBuilder.build(post.getUsers().getUsername() + "posted  a comment on your post");
        sendCommentNotification(message,post.getUsers());

    }

    public void sendCommentNotification(String message, Users user) {
        mailService.sendMail( new NotificationEmail(user.getUsername()+"Commented on your post",user.getUsername(),message));
    }

    public List<CommentsDto> getAllCommentsForPosts(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());

    }

    public List<CommentsDto> getAllCommentsForUsers(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found "+username));
        return commentRepository.findAllByUsers(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
