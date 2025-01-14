CREATE TABLE user_followers (
    id BIGINT PRIMARY KEY auto_increment,
    follower_id BIGINT NOT NULL,
    followed_id BIGINT NOT NULL,
    is_blocked BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT current_timestamp,
    FOREIGN KEY (followed_id) references users(id),
    FOREIGN KEY (follower_id) references users(id),
    UNIQUE (followed_id, follower_id)
)