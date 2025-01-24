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
    User getById(Long id);
    Page<User> findAllByUsernameContainingIgnoreCase(Pageable pageable, String username);

    @Query(value = "SELECT DISTINCT u.* FROM users u JOIN user_interest ui ON ui.user_id = u.id where ui.tag_id IN :interests", nativeQuery = true)
    List<User> findUserByInterest(@Param("interests") Set<Long> interests);
}
