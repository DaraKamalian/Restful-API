package com.example.restfulapi.service;

import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


@Service
public class AgentCryptographyService {

    public String encrypt(String data, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encodedValue = c.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encodedValue);
    }

    public String decrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, KeyGeneration.generateNewKey());
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(data);
        byte[] value = c.doFinal(decodedValue);
        String decryptedValue = new String(value);

        return decryptedValue;
    }
}

