package com.highdee.folksocialapi.integration.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.highdee.folksocialapi.GlobalTestSetupExtension;
import com.highdee.folksocialapi.TestDataUtil;
import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.PostMediaRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.services.auth.JwtService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(GlobalTestSetupExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class PostTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TestDataUtil testDataUtil;

    private Long postId;

    private String jwt ;

    @BeforeAll
    void setup(){
        System.out.println("Integration Post Test Begins");
        testDataUtil.createTestUser();
        this.jwt = jwtService.generateToken("test-user@gmail.com");
    }

    @AfterAll
    static void end(){
        System.out.println("Integration Post Test Ended");
    }

    @Test
    @Order(1)
    void testCreatePost() throws Exception {
        // Create a post media request
        PostMediaRequest postMediaRequest = new PostMediaRequest();
        postMediaRequest.setType("IMAGE");
        postMediaRequest.setUrl("https://media.istockphoto.com/id/1458782106/photo/scenic-aerial-view-of-the-mountain-landscape-with-a-forest-and-the-crystal-blue-river-in.jpg?s=2048x2048&w=is&k=20&c=jbXMS_yFujUo29EIjPd8XBsEan-PAHUcPs0Zo1-HY_U=");

        // Create a post request
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setContent("Lorem Ipsum");
        ArrayList arrayList = new ArrayList();
        arrayList.add(postMediaRequest);
        createPostRequest.setMedia(arrayList);


        // Create post
        mockMvc.perform(
            post("/api/post")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwt)
            .content(objectMapper.writeValueAsString(createPostRequest))
        ).andDo((e)-> {
            RestResponse<PostResponse> resp = objectMapper.readValue(e.getResponse().getContentAsString(),
                    new TypeReference<RestResponse<PostResponse>>(){} );

            // Set created post ID
            this.postId = resp.getData().getId();
        })
        .andExpect(jsonPath("$.code").value(ResponseCode.REQUEST_SUCCESSFUL.getCode()));
    }

    @Test
    @Order(2)
    void testGetOnePost() throws Exception {

        // Retrieve single post
        mockMvc.perform(
                get("/api/post/"+postId)
                        .header("Authorization", "Bearer "+jwt)
                )
                .andDo((e)-> System.out.println(e.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.code").value(ResponseCode.REQUEST_SUCCESSFUL.getCode()))
                .andExpect(jsonPath("$.data.id").isNotEmpty());
    }

    @Test
    @Order(3)
    void testGetAllPost() throws Exception {

        mockMvc.perform(get("/api/post").header("Authorization", "Bearer "+jwt))
                .andExpect(jsonPath("$.code").value(ResponseCode.REQUEST_SUCCESSFUL.getCode()))
                .andExpect(jsonPath("$.data.content").isArray());
    }
}
