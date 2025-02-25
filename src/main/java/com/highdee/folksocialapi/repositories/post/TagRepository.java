package com.highdee.folksocialapi.repositories.post;

import com.highdee.folksocialapi.models.post.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    List<Tag> findAllByParentId(Long parentId);
    List<Tag> findAllByNameContainingIgnoreCase(String name);
}
