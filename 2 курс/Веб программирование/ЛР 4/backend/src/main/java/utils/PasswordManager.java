package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordManager {
    public static final String SALT = "WHO_TF_IS_GIGA_NIGGA";

    public static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest((password + SALT).getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 is not supported");
        }

    }

    public static boolean checkPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }
}
