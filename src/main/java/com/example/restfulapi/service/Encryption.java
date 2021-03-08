package com.example.restfulapi.service;

import com.example.restfulapi.domain.EncryptionKey;

public class Encryption {
    public static String encrypt(String text, EncryptionKey key) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                char ch = (char) (((int) text.charAt(i) +
                        key.getValue() - 65) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int) text.charAt(i) +
                        key.getValue() - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result.toString();
    }
}
