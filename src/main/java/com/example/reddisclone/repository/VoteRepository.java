package com.example.reddisclone.repository;

import com.example.reddisclone.entity.Post;
import com.example.reddisclone.entity.Users;
import com.example.reddisclone.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository  extends JpaRepository<Vote,Long> {


    Optional<Vote> findByPostAndUsersOrderByVoteIdDesc(Post post, Users currentUser);
}
