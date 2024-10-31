package com.pet_project.pastebin.tools;


import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

public class Hashing {
    private static final String SHA256 = "SHA-256";
    private static final byte[] salt = createRandomSalt();

    public static byte[] createRandomSalt() {
        byte[] salt = new byte[16];
        Random secure_random = new SecureRandom();
        secure_random.nextBytes(salt);
        return salt;
    }

    public static byte[] createHash(byte[] input) throws Exception {
        return Hashing.createHash(input, salt);
    }

    public static byte[] createHash(
            byte[] input, byte[] salt
    ) throws Exception {
        ByteArrayOutputStream byte_Stream = new ByteArrayOutputStream();
        byte_Stream.write(salt);
        byte_Stream.write(input);
        byte[] valueToHash = byte_Stream.toByteArray();
        MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
        return messageDigest.digest(valueToHash);
    }
}
