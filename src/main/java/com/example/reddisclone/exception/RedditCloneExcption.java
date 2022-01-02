package com.example.reddisclone.exception;

public class RedditCloneExcption extends RuntimeException {
    public RedditCloneExcption(String message, Exception exception) {
        super(message, exception);
    }
    public RedditCloneExcption(String s) {
        super(s);
    }
}
