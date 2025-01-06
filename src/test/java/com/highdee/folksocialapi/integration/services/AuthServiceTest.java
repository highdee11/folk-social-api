package com.highdee.folksocialapi.integration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeAll
    static void setup(@Autowired UserRepository userRepository){
        userRepository.deleteAll();
        System.out.println("Integration AuthService Test Begins");
    }
    @AfterAll
    static void end(){
        System.out.println("Integration AuthService Test Ended");
    }

    @Test
    void testCreateAccount() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setFirstname("firstname");
        createUserRequest.setLastname("lastname");
        createUserRequest.setDob(LocalDate.now());
        createUserRequest.setPassword("secret");

        mockMvc.perform(
                post("/api/auth/create-account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(jsonPath("$.code").value("SUC001"));

    }

    @Test
    void testLogin() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@gmail.com");
        userLoginRequest.setPassword("secret");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(jsonPath("$.code").value("SUC001"))
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }
}
