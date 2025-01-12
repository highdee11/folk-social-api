package com.highdee.folksocialapi.repositories.post;

import com.highdee.folksocialapi.models.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);
    Page<Post> findAllByUser_id(Pageable pageable, Long userId);
    Page<Post> findAllByParent_id(Pageable pageable, Long parentId);
}
