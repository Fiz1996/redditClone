package com.example.reddisclone.repository;

import com.example.reddisclone.entity.Post;
import com.example.reddisclone.entity.Subreddit;
import com.example.reddisclone.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findBySubreddit(Subreddit subreddit);

    List<Post> findByUsers(Users user);
}
