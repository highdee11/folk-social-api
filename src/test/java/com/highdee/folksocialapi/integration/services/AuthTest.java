package com.highdee.folksocialapi.integration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highdee.folksocialapi.GlobalTestSetupExtension;
import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(GlobalTestSetupExtension.class)
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired UserRepository userRepository){
        System.out.println("Integration Auth Test Begins");
    }
    @AfterAll
    static void end(){
        System.out.println("Integration Auth Test Ended");
    }

    @Test
    void testCreateAccount() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setFirstname("firstname");
        createUserRequest.setUsername("myusername");
        createUserRequest.setLastname("lastname");
        createUserRequest.setDob(LocalDate.now());
        createUserRequest.setPassword("secret");

        mockMvc.perform(
                post("/api/auth/create-account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(jsonPath("$.code").value(ResponseCode.REQUEST_SUCCESSFUL.getCode()));
    }

    @Test
    void testLogin() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@gmail.com");
        userLoginRequest.setPassword("secret");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(jsonPath("$.code").value(ResponseCode.REQUEST_SUCCESSFUL.getCode()))
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }
}
