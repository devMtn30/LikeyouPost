package com.likeyoupost.back.domain;

import com.sun.jdi.request.DuplicateRequestException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static com.likeyoupost.back.config.ErrorMessage.LIKE_DUPLICATE;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Getter
@IdClass(LikeId.class)
@Entity(name = "tbl_like")
public class Like {
    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public static Like addLikeToPost(User user, Post post) {
        for (Like currentLikedUser : post.getCurrentLikedUsers()) {
            if(user.getCurrentLikedPosts().contains(currentLikedUser)) {
                throw new DuplicateRequestException(LIKE_DUPLICATE);
            }
        }
        Like like = new Like(user, post);
        user.addLikePost(like);
        post.addLikeUser(like);
        post.likePost(user);
        return like;
    }
}

