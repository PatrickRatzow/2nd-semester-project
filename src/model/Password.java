package model;

public class Password {
    private byte[] hash;
    private byte[] salt;

    public Password() {}
    public Password(byte[] password, byte[] salt) {
        this.hash = password;
        this.salt = salt;
    }

    public byte[] getHash() {
        return hash;
    }
    public byte[] getSalt() {
        return salt;
    }
}
