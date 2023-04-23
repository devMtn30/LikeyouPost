package com.likeyoupost.back.exception;

public class UnauthorizedException extends IllegalStateException{
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String s) {
        super(s);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
}
