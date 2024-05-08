package com.example.desmosclonejavafirst;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TextValidations {

    // Defining the regex pattern for a valid email address
    private static final String EMAIL_REGEX = "^[\\w._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Compiling the regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

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
     * Validates if all the credentials are not empty
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
                Toast.makeText(context, "Please enter your " + currentCredential.getCredentialName(), Toast.LENGTH_SHORT).show();
                allCredentialAttributesNotEmpty = false;
            }
        }
        return allCredentialAttributesNotEmpty;
    }

    /**
     *
     * @function passwordMatchesConfirmPassword
     * @param password the first password
     * @param confirmPassword conformation password
     * @return true if passwords are the same
     */
    public static boolean passwordMatchesConfirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

}
