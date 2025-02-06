package com.highdee.folksocialapi.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.highdee.folksocialapi.dto.validators.email.UniqueEmail;
import com.highdee.folksocialapi.dto.validators.username.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserRequest {

    private String firstname = "";

    private String lastname = "";

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
}
