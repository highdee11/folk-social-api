CREATE TABLE post_statistics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type TINYTEXT NOT NULL ,
    post_id BIGINT NOT NULL ,

    FOREIGN KEY (post_id) references posts(id)
)