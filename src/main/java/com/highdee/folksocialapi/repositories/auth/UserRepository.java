package com.highdee.folksocialapi.repositories.auth;

import com.highdee.folksocialapi.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findByEmail(String email);

    User getById(Long id);
}
