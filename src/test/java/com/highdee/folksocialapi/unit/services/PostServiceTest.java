package com.highdee.folksocialapi.unit.services;

import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.repositories.post.PostRepository;
import com.highdee.folksocialapi.services.post.post.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOnePost(){
//        Post sample = new Post();
//        sample.setId(1l);
//        sample.setUser(new User());
//        sample.setContent("Lorem Ipsum");
//
//        when(postRepository.getById(1l)).thenReturn(sample);
//
//        Post post = postService.getOne(1l);
//        assertEquals(post.getId(), 1l);
    }
}
