package com.likeyoupost.back.controller;

import com.likeyoupost.back.argumentresolver.Login;
import com.likeyoupost.back.dto.*;
import com.likeyoupost.back.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public Page<PostFindResponse> postList(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "limit", defaultValue = "10") int limit,
                                           @Login LoginUser user) {
        return postService.getPosts(page, limit, user);
    }

    @PostMapping
    public Long postWrite(@Validated @RequestBody PostWriteRequest request,
                          @Login LoginUser user) {
        return postService.writePost(request, user);
    }

    @PutMapping("/{postId}")
    public Long postUpdate(@PathVariable Long postId,
                           @Validated @RequestBody PostUpdateRequest request,
                           @Login LoginUser user) {
        return postService.updatePost(postId, request, user);
    }

    @DeleteMapping("/{postId}")
    public Long postDelete(@PathVariable Long postId,
                           @Login LoginUser user) {
        return postService.deletePost(postId, user);
    }

    @PostMapping("/{postId}/like")
    public int postLike(@PathVariable Long postId,
                         @Login LoginUser user) {
        return postService.addLike(postId, user);
    }
}
