package me.chiqors.minimarket_backend.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {
    public static String getMD5hash(String originalPassword) {
        try {
            // Create an instance of the MD5 algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convert the input string to bytes
            byte[] inputBytes = originalPassword.getBytes();

            // Compute the MD5 hash
            byte[] hashedBytes = md.digest(inputBytes);

            // Convert the hashed bytes to a hexadecimal string representation
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }
}
