package entity;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class Password implements Comparable<Password> {
    private static final int ITERATIONS = (int) Math.pow(2, 16);
    private static final int KEY_LENGTH = 512;

    private final byte[] salt;
    private final byte[] hash;

    public Password(byte[] passwordHash) {
        final byte[] salt = Arrays.copyOfRange(passwordHash, 0, 16);
        final byte[] hash = Arrays.copyOfRange(passwordHash, 16, 80);

        this.salt = salt;
        this.hash = hash;
    }
    public Password(String password) {
        final byte[] salt = generateSalt();
        final byte[] hash = hashPassword(password, salt);

        this.salt = salt;
        this.hash = hash;
    }
    public Password(String password, byte[] salt) {
        final byte[] hash = hashPassword(password, salt);

        this.salt = salt;
        this.hash = hash;
    }

    public byte[] getBytes() {
        return ByteBuffer.wrap(new byte[80])
                .put(salt)
                .put(hash)
                .array();
    }

    private byte[] generateSalt() {
        final SecureRandom random = new SecureRandom();
        final byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }

    private byte[] hashPassword(final String password, final byte[] salt) {
        byte[] bytes = new byte[64];

        try {
            final KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

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
