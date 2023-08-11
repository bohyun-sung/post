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
        return getEncryptor().encrypt(text);
    }

    // 복호화 decryptAES256
    public static String decrypt(String encryptedText) {
        return getEncryptor().decrypt(encryptedText);
    }

    private static TextEncryptor getEncryptor() {
        if (encryptor == null) {
            encryptor = Encryptors.text(
                    getSecretKey(),
                    CharBuffer.wrap(Hex.encode(getSalt().getBytes(StandardCharsets.UTF_8)))
            );
        }
        return encryptor;
    }

    /**
     * TODO yml 로 이전
     * @return JWT secretKey
     */
    public static String getSecretKey() {
        return "MySecretKeyTestKeySizeIs$64ByteTestKey";
    }

    public static String getSalt() {
        return "salt";
    }

}

