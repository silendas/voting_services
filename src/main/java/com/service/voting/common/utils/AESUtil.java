package com.service.voting.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding"; // Padding untuk AES
    private static final String SECRET_KEY = "1234567890123456"; // Kunci 16 karakter untuk AES-128

    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedData));
        return new String(decryptedBytes, "UTF-8");
    }
}
