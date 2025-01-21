package com.highdee.folksocialapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FolkSocialApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FolkSocialApiApplication.class, args);
    }

}
