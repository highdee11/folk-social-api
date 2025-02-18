package com.highdee.folksocialapi.repositories.auth;

import com.highdee.folksocialapi.models.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findByEmail(String email);
    User findByUsername(String username);
    User getById(Long id);
    Page<User> findAllByUsernameContainingIgnoreCase(Pageable pageable, String username);

    @Query(value = "SELECT DISTINCT u.* FROM users u " +
            "JOIN user_interest ui ON ui.user_id = u.id " +
            "LEFT JOIN user_followers uf ON uf.followed_id = u.id AND uf.follower_id = :userId " +
            "WHERE ui.tag_id IN :interests AND uf.follower_id IS NULL AND u.id != :userId",
            nativeQuery = true)
    Page<User> findUserByInterest(Pageable pageable, @Param("userId") Long id, @Param("interests") Set<Long> interests);
}
