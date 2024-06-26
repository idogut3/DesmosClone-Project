package com.example.desmosclonejavafirst.validations.text_validations;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.desmosclonejavafirst.validations.Validation;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TextValidation class provides utility methods for validating text inputs such as email addresses,
 * password matching, and checking if all credential attributes are not empty.
 */
public class TextValidation extends Validation {

    // Defining the regex pattern for a valid email address
    protected static final String EMAIL_REGEX = "^[\\w._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Compiling the regex pattern
    protected static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Validates an email address.
     *
     * @param email the email address to validate
     * @return true if the email address is valid, false otherwise
     * @function isEmailValid
     */
    public static boolean isEmailValid(String email) {
        // Create a matcher for the input email
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        // Return whether the email matches the pattern
        return matcher.matches();
    }

    /**
     * Validates if all the credentials are not empty, if one or more credentials is not empty, prints an error message
     *
     * @param credentialAttributes a list of all the credential attributes in the current context
     * @return true if the all credential attributes are not Empty
     * @function isAllCredentialAttributesNotEmpty
     */
    public static boolean isAllCredentialAttributesNotEmpty(LinkedList<CredentialAttribute> credentialAttributes, Context context) {
        boolean allCredentialAttributesNotEmpty = true;
        for (int i = 0; i < credentialAttributes.size(); i++) {
            CredentialAttribute currentCredential = credentialAttributes.get(i);
            if (TextUtils.isEmpty(currentCredential.getValue())) {
                currentCredential.getEditText().setError("Please enter your " + currentCredential.getCredentialName());
                allCredentialAttributesNotEmpty = false;
            }
        }
        if (!allCredentialAttributesNotEmpty) {
            Toast.makeText(context, "One or more of the fields listed are empty, please fill them up", Toast.LENGTH_SHORT).show();
        }
        return allCredentialAttributesNotEmpty;
    }

    /**
     * @param password        the first password
     * @param confirmPassword conformation password
     * @return true if passwords are the same
     * @function passwordMatchesConfirmPassword
     */
    public static boolean passwordMatchesConfirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    /**
     * Validates all text inputs for sign up. Checks if all credential attributes are not empty
     * and if the password matches the confirmation password.
     *
     * @param credentials      a list of all the credential attributes in the sign up context
     * @param context          the context of the calling activity or application
     * @param password         the password input
     * @param confirmPassword  the confirmation password input
     * @return true if all text validations pass, false otherwise
     */
    public static boolean passedAllTextValidationsForSignUp(LinkedList<CredentialAttribute> credentials, Context context, String password, String confirmPassword) {
        return isAllCredentialAttributesNotEmpty(credentials, context) && passwordMatchesConfirmPassword(password, confirmPassword);
    }

    /**
     * Validates all text inputs for login. Checks if all credential attributes are not empty.
     *
     * @param credentials a list of all the credential attributes in the login context
     * @param context     the context of the calling activity or application
     * @return true if all text validations pass, false otherwise
     */
    public static boolean passedAllTextValidationsForLogin(LinkedList<CredentialAttribute> credentials, Context context) {
        return isAllCredentialAttributesNotEmpty(credentials, context); //TODO:: ADD EMAIL CHECK SOMETIME
    }


}
