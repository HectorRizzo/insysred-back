package com.insysred.isp.util;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class Encriptar {
    private static final String ALGORITHM = "AES";
    private static final String MODE = "AES/CBC/PKCS5Padding"; // Modo de cifrado y relleno
    private static final String KEY = "0123456789abcdef"; // Clave de 16 bytes (128 bits)

    public static String encrypt(String value) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(KEY.getBytes("UTF-8"));
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), ALGORITHM);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedValue) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(KEY.getBytes("UTF-8"));
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), ALGORITHM);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        byte[] originalBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(originalBytes);
    }

}
