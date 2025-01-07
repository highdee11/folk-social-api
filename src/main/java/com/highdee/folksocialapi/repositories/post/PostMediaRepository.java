package com.highdee.folksocialapi.repositories.post;

import com.highdee.folksocialapi.models.post.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {
}
