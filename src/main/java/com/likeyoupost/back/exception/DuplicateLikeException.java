package com.likeyoupost.back.exception;

public class DuplicateLikeException extends IllegalStateException{
    public DuplicateLikeException() {
        super();
    }

    public DuplicateLikeException(String s) {
        super(s);
    }

    public DuplicateLikeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateLikeException(Throwable cause) {
        super(cause);
    }
}
