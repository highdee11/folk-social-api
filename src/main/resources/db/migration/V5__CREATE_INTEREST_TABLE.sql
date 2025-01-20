# INTEREST
CREATE TABLE tags (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name TINYTEXT NOT NULL,
    parent_id BIGINT,
    FOREIGN KEY (parent_id) REFERENCES tags(id)
);

# USER_INTEREST
CREATE TABLE user_interest (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   user_id BIGINT NOT NULL,
   tag_id BIGINT NOT NULL,
   FOREIGN KEY (user_id) REFERENCES users(id),
   FOREIGN KEY (tag_id) REFERENCES tags(id)
);

# TAG_POST
CREATE TABLE post_tag (
      id BIGINT PRIMARY KEY AUTO_INCREMENT,
      tag_id BIGINT NOT NULL ,
      post_id BIGINT NOT NULL ,
      FOREIGN KEY (tag_id) REFERENCES tags(id),
      FOREIGN KEY (post_id) REFERENCES posts(id)
);