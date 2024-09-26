package Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class ConfigKeyLoader {

    // 100% basically just storing they key to our passwords in what is effectively a text document
    // I could list 90 reasons why this sucks and 9 ways to do it better, but I'm not going to.
    public static SecretKey getSecretKeyFromConfig() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/java/Util/config.properties")) {
            properties.load(input);
        }

        String aesKeyBase64 = properties.getProperty("AES_KEY");

        // Always check
        if (aesKeyBase64 == null || aesKeyBase64.isEmpty()) {
            throw new IllegalStateException("AES_KEY is not set in config.properties");
        }

        byte[] decodedKey = Base64.getDecoder().decode(aesKeyBase64);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
