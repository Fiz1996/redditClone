package com.example.reddisclone.entity;

import com.example.reddisclone.exception.SubredditNotFountException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    private int direction;

     VoteType(int direction) {

    }

    public static VoteType lookup(Integer direction) {
         return Arrays.stream(VoteType.values())
                 .filter(value -> value.getDeclaringClass().equals(direction))
                 .findAny()
                 .orElseThrow(()-> new SubredditNotFountException("Vote not found"));
    }
}
