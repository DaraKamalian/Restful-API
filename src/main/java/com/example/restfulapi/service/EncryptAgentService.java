package com.example.restfulapi.service;

import com.example.restfulapi.domain.Agent;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class EncryptAgentService {

    public static SecretKey createKey(Agent agent) throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        byte[] iv = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(128, secureRandom);

        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    public static Agent encryptAgent(Agent agent) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        cipher.init(Cipher.ENCRYPT_MODE, createKey(agent));

        byte[] cipherAgentFirstName = cipher.doFinal(agent.getFirstName().getBytes());
        byte[] cipherAgentLastName = cipher.doFinal(agent.getLastName().getBytes());

        return new Agent(cipherAgentFirstName.toString(),cipherAgentLastName.toString());

    }

    public static Agent decryptAgent(Agent agent) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, createKey(agent) );

        byte[] decipherAgentFirstName = cipher.doFinal(agent.getFirstName().getBytes());
        byte[] decipherAgentLastName = cipher.doFinal(agent.getLastName().getBytes());

        return new Agent(decipherAgentFirstName.toString(),decipherAgentLastName.toString());

    }

}
