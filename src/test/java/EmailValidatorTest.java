import Util.BouncyCastleAESUtil;
import Util.EmailValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {
    @Test
    public void testIsValidEmailWithNoFormating() {
        String email = "email";
        assertFalse(EmailValidator.isValidEmail(email));
    }

    @Test
    public void testIsValidEmailWithSpace() {
        String email = "email @gmail .com";
        assertFalse(EmailValidator.isValidEmail(email));
    }

    @Test
    public void testIsValidEmailWithNoAmpersand() {
        String email = "emailgmail.com";
        assertFalse(EmailValidator.isValidEmail(email));
    }

    @Test
    public void testIsValidEmailWithValidEmail() {
        String email = "email@gmail.com";
        assertTrue(EmailValidator.isValidEmail(email));
    }
}
