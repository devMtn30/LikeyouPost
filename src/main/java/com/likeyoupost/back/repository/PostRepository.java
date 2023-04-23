package com.likeyoupost.back.repository;

import com.likeyoupost.back.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT p FROM " +
            "tbl_post p " +
            "LEFT JOIN FETCH p.author " +
            "LEFT JOIN FETCH p.currentLikedUsers " +
            "WHERE p.deletedAt IS NULL " +
            "ORDER BY p.createdAt DESC",
            countQuery = "SELECT COUNT(p) FROM " +
                    "tbl_post p " +
                    "LEFT JOIN p.author " +
                    "LEFT JOIN p.currentLikedUsers " +
                    "WHERE p.deletedAt IS NULL")
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}