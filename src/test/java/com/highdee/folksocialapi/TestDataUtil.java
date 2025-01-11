package com.highdee.folksocialapi;

import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataUtil {

    @Autowired
    private UserRepository userRepository;

    public void createTestUser(){
        String email = "test-user@gmail.com";
        boolean existsByEmail = userRepository.existsByEmail(email);

        if(!existsByEmail){

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User testUser = new User();
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            testUser.setEmail(email);
            testUser.setPassword(encoder.encode("secret"));

            userRepository.save(testUser);
        }
    }

}
