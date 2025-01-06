package com.highdee.folksocialapi.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.highdee.folksocialapi.dto.validators.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CreateUserRequest {
    @UniqueEmail
    @Email(message = "Enter a valid email address")
    public String email;

    @NotBlank(message = "Firstname is required")
    public String firstname;

    @NotBlank(message = "Lastname is required")
    public String lastname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate dob;

    @NotBlank(message="Password is required")
    public String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }
}
