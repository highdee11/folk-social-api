package com.highdee.folksocialapi.models;

import com.highdee.folksocialapi.models.auth.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "user_followers")
public class UserFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private User followed;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFollowing() {
        return followed;
    }

    public void setFollowing(User followed) {
        this.followed = followed;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
