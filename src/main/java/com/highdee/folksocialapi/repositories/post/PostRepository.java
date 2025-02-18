package com.highdee.folksocialapi.repositories.post;

import com.highdee.folksocialapi.models.post.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);
    Page<Post> findAllByUser_id(Pageable pageable, Long userId);
    Page<Post> findAllByParent_id(Pageable pageable, Long parentId);

    @Query(value = "SELECT p.user_id, p.id, p.parent_id, p.content, p.created_at FROM posts p " +
            "JOIN user_followers uf ON uf.followed_id = user_id " +
            "WHERE uf.follower_id IN (:userId) AND p.parent_id is NULL",
            nativeQuery = true)
    Page<Post> findAllThroughFollowing(Pageable pageable, @Param("userId") Long userId);
}

