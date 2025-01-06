CREATE TABLE media (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    type ENUM('image', 'video') NOT NULL,
    url varchar(225) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    FOREIGN KEY (post_id) REFERENCES posts(id)
)