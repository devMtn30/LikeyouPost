package com.likeyoupost.back.service;

import com.likeyoupost.back.domain.Post;
import com.likeyoupost.back.domain.User;
import com.likeyoupost.back.dto.LoginUser;
import com.likeyoupost.back.dto.PostFindResponse;
import com.likeyoupost.back.dto.PostUpdateRequest;
import com.likeyoupost.back.dto.PostWriteRequest;
import com.likeyoupost.back.exception.PostNotFoundException;
import com.likeyoupost.back.exception.UserNotFoundException;
import com.likeyoupost.back.repository.PostRepository;
import com.likeyoupost.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.likeyoupost.back.config.ErrorMessage.POST_NOT_FOUND;
import static com.likeyoupost.back.config.ErrorMessage.USER_NOT_FOUND;
import static com.likeyoupost.back.domain.Like.addLikeToPost;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long writePost(PostWriteRequest writeRequest, LoginUser loginUser) {
        User user = getSavedUser(loginUser);
        Post post = postRepository.save(PostWriteRequest.getPost(writeRequest, user));

        return post.getPostId();
    }

    public Long updatePost(Long postId, PostUpdateRequest updateRequest, LoginUser loginUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
        post.patchPost(updateRequest.getTitle(), updateRequest.getContent(), getSavedUser(loginUser));

        return post.getPostId();
    }

    public Long deletePost(Long postId, LoginUser loginUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
        User user = getSavedUser(loginUser);
        post.deletePost(user);

        return post.getPostId();
    }

    public int addLike(Long postId, LoginUser loginUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
        User user = getSavedUser(loginUser);

        addLikeToPost(user, post);
        return post.getLikeCount();
    }

    @Transactional(readOnly = true)
    public Page<PostFindResponse> getPosts(int page, int limit, LoginUser loginUser) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<PostFindResponse> responseList = getPostFindResponseList(loginUser, posts);

        return new PageImpl<>(responseList, pageable, posts.getTotalElements());
    }

    private List<PostFindResponse> getPostFindResponseList(LoginUser loginUser, Page<Post> posts) {
        User user = getSavedUser(loginUser);

        List<PostFindResponse> postDtos = posts.getContent().stream().map(post -> {
            boolean isLiked = user != null && post.getCurrentLikedUsers().stream()
                    .anyMatch(like -> like.getUser().equals(user));
            return new PostFindResponse(post.getPostId(), post.getTitle(), post.getContent(),
                    post.getLikeCount(), isLiked, post.getAuthor().getAccountType().getName());
        }).collect(Collectors.toList());
        return postDtos;
    }

    private User getSavedUser(LoginUser loginUser) {
        if(ObjectUtils.isEmpty(loginUser)) {
            return null;
        }
        Long userId = loginUser.getUserId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return optionalUser.get();
    }

}