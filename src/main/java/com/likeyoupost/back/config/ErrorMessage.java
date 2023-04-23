package com.likeyoupost.back.config;

public class ErrorMessage {
    public final static String POST_NOT_FOUND = "게시글을 찾을 수 없습니다.";
    public final static String LIKE_DUPLICATE = "이미 좋아요를 누른 게시물입니다.";
    public final static String USER_NOT_FOUND = "해당 회원을 찾을 수 없습니다.";
    public final static String NOT_AUTH_USER = "회원이 아닌 외부 사용자의 요청입니다.";
    public final static String DENY_AUTHORITY = "요청할 권한이 없습니다.";
}
