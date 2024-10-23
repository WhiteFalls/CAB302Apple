import Util.BouncyCastleAESUtil;
import Util.ConfigKeyLoader;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigKeyLoaderTest {
    private static final boolean DEBUG_MODE = false;  // Set this to true to disable tests

    @Test
    public void testGetSecretKeyFromConfig() throws IOException {
        SecretKey key = ConfigKeyLoader.getSecretKeyFromConfig();
        assertNotNull(key);
    }
}
