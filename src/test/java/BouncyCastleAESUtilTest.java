import People.Person;
import Tasks.Task;
import Util.BouncyCastleAESUtil;
import Util.ConfigKeyLoader;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BouncyCastleAESUtilTest {
    private static final boolean DEBUG_MODE = false;  // Set this to true to disable tests

    @Test
    public void testGenerateIV() {
        byte[] iv = BouncyCastleAESUtil.generateIv();
        assertNotNull(iv);
    }

    @Test
    public void testEncryptionAndDecryption() throws Exception {
        String message = "message";
        SecretKey key = ConfigKeyLoader.getSecretKeyFromConfig();
        byte[] iv = BouncyCastleAESUtil.generateIv();

        String encryptedMessage = BouncyCastleAESUtil.encrypt(message, key, iv);
        String decryptedMessage = BouncyCastleAESUtil.decrypt(encryptedMessage, key, iv);
        assertEquals(message, decryptedMessage);

    }
}
