package Server.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class PasswordHasher {
    public static final String SALT = "UltraMegaSalt";

    public static String hashPassword(String password) {
        String passwordWithSalt = password + SALT;

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(passwordWithSalt.getBytes());

        return HexFormat.of().formatHex(hash);
    }
}
