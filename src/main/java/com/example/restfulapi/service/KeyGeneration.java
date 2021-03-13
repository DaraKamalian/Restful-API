package com.example.restfulapi.service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class KeyGeneration {

    public static Key generateNewKey() throws NoSuchAlgorithmException {
        return new SecretKeySpec(new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, "AES");
    }


}
