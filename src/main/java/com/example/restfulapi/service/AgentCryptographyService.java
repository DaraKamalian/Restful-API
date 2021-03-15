package com.example.restfulapi.service;

import org.springframework.stereotype.Service;


import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;


@Service
public class AgentCryptographyService {

    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    SecretKey secretKey = keyGen.generateKey();
    byte[] ivBytes = new byte[12];
    GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivBytes);

    String toEncrypt = "";

    public AgentCryptographyService() throws NoSuchAlgorithmException {
    }

    public String[] getCryptographyValues(String data) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] encryptedData = cipher.doFinal(data.getBytes());
        byte[] toShow = Base64.getEncoder().encode(encryptedData);

        String finalEncryptedValue = new String(toShow);

        byte[] temp =toShow;
        Cipher aes = Cipher.getInstance("AES/GCM/NoPadding");
        aes.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] result = Base64.getDecoder().decode(temp);
        byte[] decrypted = aes.doFinal(result);

        String finalDecryptedValue = new String(decrypted);

        String[] Results = new String[2];
        Results[0] = finalEncryptedValue;
        Results[1] = finalDecryptedValue;

        System.out.println(Results[1]);

        return Results;
    }

//    public String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException,
//            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
//
//        toEncrypt = data;
//        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
//
//        byte[] encryptedData = cipher.doFinal(data.getBytes());
//        byte[] toShow = Base64.getEncoder().encode(encryptedData);
//
//        String finalEncryptedValue = new String(toShow);
//
//        return finalEncryptedValue;
//    }

//    public String decrypt(String encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException,
//            InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
//            InvalidAlgorithmParameterException {
//
//        byte[] temp = encrypt(toEncrypt).getBytes();
//        Cipher aes = Cipher.getInstance("AES/GCM/NoPadding");
//        aes.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
//        byte[] result = Base64.getDecoder().decode(temp);
//        byte[] decrypted = aes.doFinal(result);
//
//        String finalDecryptedValue = new String(decrypted);
//
//        toEncrypt = "";
//        return finalDecryptedValue;
//    }
}

