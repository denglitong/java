package com.denglitong.category_articles_backend.utils;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
public class Encrypt {

    private static final String SHA_256 = "SHA-256";

    public static String SHA256(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
            byte[] hash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String password = "123456";
        System.out.println(Encrypt.SHA256(password));
    }
}
