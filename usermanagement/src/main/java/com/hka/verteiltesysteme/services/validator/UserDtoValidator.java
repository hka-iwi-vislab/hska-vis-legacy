package com.hka.verteiltesysteme.services.validator;

import com.hka.verteiltesysteme.dto.UserDto;
import com.hka.verteiltesysteme.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
@RequiredArgsConstructor
public class UserDtoValidator implements Validator {

    UserRepo userRepo;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, userDto.firstname(), "error.firstname.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, userDto.lastname(), "error.lastname.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, userDto.username(), "error.username.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, userDto.password1(), "error.password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, userDto.password2(), "error.password.required");

        if(!userDto.password1().equals(userDto.password2())) {
            errors.rejectValue("password", "error.password.notEqual");
        }

        if(userRepo.findByUsername(userDto.username()).isPresent()) {
            errors.rejectValue(userDto.username(), "error.userName.alreadyInUse");
        }
    }
}
