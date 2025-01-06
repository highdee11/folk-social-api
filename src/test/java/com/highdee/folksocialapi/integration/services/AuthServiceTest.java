package com.highdee.folksocialapi.integration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
    }

    @Test
    void testCreateAccount() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setFirstname("firstname");
        createUserRequest.setLastname("lastname");
        createUserRequest.setDob(LocalDate.now());
        createUserRequest.setPassword("secret");


//        mockMvc.perform(
//                post("/api/auth/create-account")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(String.valueOf(createUserRequest)))
//                .andDo(System.out::println)
//                .andExpect(jsonPath("$.code").value("SUC001"));

    }

}
