package com.highdee.folksocialapi.dto.validators;

import com.highdee.folksocialapi.repositories.auth.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    final private UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByEmail(s);
    }
}
