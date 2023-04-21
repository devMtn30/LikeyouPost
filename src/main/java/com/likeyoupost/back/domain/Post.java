package com.likeyoupost.back.domain;

import com.likeyoupost.back.exception.UnauthorizedException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static com.likeyoupost.back.config.ErrorMessage.DENY_AUTHORITY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "tbl_post")
public class Post extends BaseDate{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;
    private String title;
    private String content;
    private int likeCount;

    @ManyToOne(fetch = LAZY)
    private User author;

    @OneToMany(mappedBy = "post",cascade = ALL,orphanRemoval = true)
    private Set<Like> currentLikedUsers;

    protected Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public static Post createPost(String title, String content, User user) {
        Post post = new Post(title, content, user);
        user.addAuthorPost(post);
        return post;
    }

    protected void likePost(User user) {
        likeCount++;
    }

    protected void addLikeUser(Like like) {
        currentLikedUsers.add(like);
    }

    public void patchPost(String title, String content, User user) {
        authorizationCheck(user);
        this.title = title;
        this.content = content;
    }

    public void deletePost(User user) {
        authorizationCheck(user);
        this.deletedAt = LocalDateTime.now();
    }

    private void authorizationCheck(User user) {
        if (!author.equals(user)) {
            throw new UnauthorizedException(DENY_AUTHORITY);
        }
    }
}
