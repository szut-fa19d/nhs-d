package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordHash {
    private final byte[] salt;
    private SecretKeyFactory factory;

    public PasswordHash(Long uid) {
        salt = uid.toString().getBytes();

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String hashPassword(String password) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

            byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();

            return enc.encodeToString(hashedPassword);
        } catch (InvalidKeySpecException e){
            e.printStackTrace();
        }
        return null;
    }
}
