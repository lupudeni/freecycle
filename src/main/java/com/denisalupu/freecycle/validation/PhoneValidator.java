package com.denisalupu.freecycle.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The regex pattern below is a composition of patterns that match the following formats:
 * "0123456789","012 345 6789", "(012) 345-6789", "+000 (012) 345-6789",
 * "012 345 678", "+000 012 345 678", "012 34 56 78", "+000 012 34 56 78";
 * Pattern composition: https://www.baeldung.com/java-regex-validate-phone-numbers#multiple
 */
public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    String patterns
            = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";


    public void initialize(ValidPhone constraint) {
    }

    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();

    }
}
