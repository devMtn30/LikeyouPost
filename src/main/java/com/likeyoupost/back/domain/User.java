package com.likeyoupost.back.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity(name = "tbl_user")
public class User extends BaseDate implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    private String nickname;
    private String accountId;
    private boolean quit;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Like> currentLikedPosts;

    @OneToMany(mappedBy = "author")
    private List<Post> authoredPosts;

    protected void addLikePost(Like like) {
        currentLikedPosts.add(like);
    }

    protected void addAuthorPost(Post post) {
        authoredPosts.add(post);
    }

    public User(String nickname, String accountId, AccountType accountType) {
        this.nickname = nickname;
        this.accountId = accountId;
        this.accountType = accountType;
    }
}
