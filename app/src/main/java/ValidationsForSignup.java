import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationsForSignup {

    /**
     * All the validations for the email address :
     */

    // Defining the regex pattern for a valid email address
    private static final String EMAIL_REGEX = "^[\\w._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Compiling the regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Validates an email address.
     *
     * @param email the email address to validate
     * @return true if the email address is valid, false otherwise
     */
    public static boolean validateEmail(String email) {
        // Create a matcher for the input email
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        // Return whether the email matches the pattern
        return matcher.matches();
    }
}
