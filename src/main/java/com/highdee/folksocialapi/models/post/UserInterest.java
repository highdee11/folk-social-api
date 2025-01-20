package com.highdee.folksocialapi.models.post;

import com.highdee.folksocialapi.models.auth.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_interest")
public class UserInterest {

    @Id
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
