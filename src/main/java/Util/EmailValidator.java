package Util;

import java.util.regex.Pattern;

public class EmailValidator {

    // Regex pattern for validating email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /**
     * Validation for the email to be in correct format
     * @param email The email to be validated
     * @return True if the email is in the correct formal, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}
