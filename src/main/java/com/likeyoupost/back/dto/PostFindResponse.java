package com.likeyoupost.back.dto;

import lombok.Getter;

@Getter
public class PostFindResponse {
    private Long postId;
    private String title;
    private String content;
    private int likeCount;
    private boolean isLiked;
    private String accountType;

    public PostFindResponse(Long postId, String title, String content, int likeCount, boolean isLiked, String accountType) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.accountType = accountType;
    }
}
