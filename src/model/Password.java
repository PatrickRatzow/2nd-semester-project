package model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class Password implements Comparable<Password> {
    private final byte[] salt;
    private final byte[] hash;

    public Password(byte[] passwordHash) {
        byte[] salt = Arrays.copyOfRange(passwordHash, 0, 16);
        byte[] hash = Arrays.copyOfRange(passwordHash, 16, 48);

        this.salt = salt;
        this.hash = hash;
    }
    public Password(String password) {
        byte[] salt = generateSalt();
        byte[] hash = hashPassword(password, salt);

        this.salt = salt;
        this.hash = hash;
    }
    public Password(String password, byte[] salt) {
        byte[] hash = hashPassword(password, salt);

        this.salt = salt;
        this.hash = hash;
    }

    public byte[] getBytes() {
        return ByteBuffer.wrap(new byte[48])
                .put(salt)
                .put(hash)
                .array();
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }

    private static byte[] hashPassword(String password, byte[] salt) {
        byte[] bytes = new byte[32];

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, (int) Math.pow(2, 16), 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            bytes = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public boolean equals(String password) {
        return compareTo(new Password(password, salt)) > 0;
    }

    @Override
    public int compareTo(Password password) {
        return Arrays.equals(getBytes(), password.getBytes()) ? 1 : -1;
    }
}
