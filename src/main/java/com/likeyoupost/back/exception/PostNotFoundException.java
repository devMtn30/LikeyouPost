package com.likeyoupost.back.exception;

public class PostNotFoundException extends IllegalArgumentException{
    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(String s) {
        super(s);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundException(Throwable cause) {
        super(cause);
    }
}
