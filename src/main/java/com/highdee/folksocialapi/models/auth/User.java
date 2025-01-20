package com.highdee.folksocialapi.models.auth;

import com.highdee.folksocialapi.models.UserFollow;
import com.highdee.folksocialapi.models.post.Tag;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column(nullable = true)
    private LocalDate dob;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFollow> followings;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFollow> followers;

    @ManyToMany
    @JoinTable(name = "user_interest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> interests = new HashSet<>();


    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<Tag> getInterests() {
        return interests;
    }

    public void setInterests(List<Tag> interests) {
        this.interests.addAll(interests);
    }
}
