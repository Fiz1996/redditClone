package com.example.reddisclone.exception;

public class PostNotFoundException  extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
