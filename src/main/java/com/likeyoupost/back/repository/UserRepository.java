package com.likeyoupost.back.repository;

import com.likeyoupost.back.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);

    @Query(value = "Select u From tbl_user u where userId = :userId")
    @Cacheable(value = "users", key = "#userId")
    Optional<User> findByIdCache(Long userId);
}