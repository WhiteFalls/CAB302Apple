package Util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

public class BouncyCastleAESUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";


    /**
     * Encrypts the password
     * @param plainText The password to be encrypted
     * @param key The key of the encryption
     * @param iv Initialization vector for the encryption
     * @return Returns an encrypted password
     * @throws Exception
     */
    public static String encrypt(String plainText, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Decrypts the encrypted text
     * @param cipherText The text to be decrypted
     * @param key The key of the encrypted text
     * @param iv The initialization vector of the encrypted text
     * @return A decrypted text
     * @throws Exception
     */
    public static String decrypt(String cipherText, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decrypted);
    }

    /**
     * Generates an initialization vector
     * @return An initialization vector
     */
    public static byte[] generateIv() {
        byte[] iv = new byte[16]; // AES uses 16-byte IV for CBC mode
        new SecureRandom().nextBytes(iv);
        return iv;
    }


}
