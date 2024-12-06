package com.example.my_android_training.encryption;

import android.os.Build;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtils {

    // 固定的盐值，确保加密结果一致
    private static final String FIXED_SALT = "mySecretSalt";  // 可以是任何固定值

    // 使用SHA-256和固定盐值加密
    public static String hashWithFixedSalt(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String inputWithSalt = input + FIXED_SALT;  // 将盐值与用户 ID 结合
        byte[] hash = digest.digest(inputWithSalt.getBytes());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(hash);
        } else {
            return md5WithFixedSalt(input);
        }
    }

    // 使用 MD5 和固定盐值加密
    public static String md5WithFixedSalt(String input) {
        String result = input;
        try {
            // 拼接输入和盐值
            String inputWithSalt = input + FIXED_SALT;

            // 获取 MD5 实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 执行哈希
            byte[] hash = md.digest(inputWithSalt.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // 补零
                }
                hexString.append(hex);
            }

            result = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("", "NoSuchAlgorithmException");
        }
        return result;
    }
}
