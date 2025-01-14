package com.highdee.folksocialapi.dto.validators.username;

import com.highdee.folksocialapi.repositories.auth.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    final private UserRepository userRepository;
    @Autowired
    public UniqueUsernameValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByUsername(s);
    }
}
