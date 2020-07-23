package com.denisalupu.freecycle.validation;

import com.denisalupu.freecycle.service.AuthenticationService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UserNameValidator implements ConstraintValidator<ValidUserName, String> {
   private static final int MIN_LENGTH = 5;
   private static final int MAX_LENGTH = 30;

   private final AuthenticationService authenticationService;

   @Override
   public void initialize(ValidUserName constraint) {
   }

   @Override
   public boolean isValid(String userName, ConstraintValidatorContext context) {
      return authenticationService.isUserNameUnique(userName)
              && userName.length() >= MIN_LENGTH
              && userName.length() <= MAX_LENGTH;
   }
}
