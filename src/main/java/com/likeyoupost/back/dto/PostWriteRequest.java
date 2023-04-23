package com.likeyoupost.back.dto;

import com.likeyoupost.back.domain.Post;
import com.likeyoupost.back.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostWriteRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public static Post getPost(PostWriteRequest request, User user) {
        return Post.createPost(request.getTitle(),request.getContent(), user);
    }
}
