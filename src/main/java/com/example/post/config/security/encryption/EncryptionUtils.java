package com.example.post.config.security.encryption;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class EncryptionUtils {

    private static TextEncryptor encryptor;

    // 암호화 encryptAES256
    public static String encrypt(String text) {
        // 평문 데이터를 암호화
        return _getEncryptor().encrypt(text);
    }

    // 복호화 decryptAES256
    public static String decrypt(String encryptedText) {
        return _getEncryptor().decrypt(encryptedText);
    }

    private static TextEncryptor _getEncryptor() {
        if (encryptor == null) {
            encryptor = Encryptors.text(
                    getSecretKey(),
                    CharBuffer.wrap(Hex.encode(getSalt().getBytes(StandardCharsets.UTF_8)))
            );
        }
        return encryptor;
    }

    public static String getSecretKey() {
        return "/U0RbIKgNpuXV.s.L0V.095j9bltcruqEYoEthB5wce.BIC";
    }

    public static String getSalt() {
        return "salt";
    }

}

