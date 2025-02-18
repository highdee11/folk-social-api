package com.highdee.folksocialapi.services.post.post;

import com.highdee.folksocialapi.repositories.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostStatServiceImpl implements PostStatService{

    @Autowired
    public PostRepository repository;

    @Override
    public int getUserPostCount(Long userId) {
        return repository.findUserPostCount(userId);
    }

}
