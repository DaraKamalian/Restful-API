package com.example.restfulapi.service;

import com.example.restfulapi.domain.Agent;
import com.example.restfulapi.domain.EncryptionKey;
import com.example.restfulapi.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

@Repository
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;


    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public void save(Agent agent) {
        agentRepository.save(agent);
    }

    public Agent get(Long id) {
        return agentRepository.findById(id).get();
    }

    public void delete(Long id) {
        agentRepository.deleteById(id);
    }

    public Agent encryptAgent(Agent agent) throws NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidKeyException {

        Cipher firstNameEncryptionCipher = Encryption.createEncryptionCipher(agent.getFirstName());
        Cipher lastNameEncryptionCipher = Encryption.createEncryptionCipher(agent.getLastName());

        firstNameEncryptionCipher.doFinal(agent.getFirstName().getBytes());
        lastNameEncryptionCipher.doFinal(agent.getLastName().getBytes());

        return new Agent(firstNameEncryptionCipher.toString(), lastNameEncryptionCipher.toString());
    }

    public Agent decryptAgent(Agent agent) throws NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidKeyException {


        Cipher firstNameDecryptionCipher = Encryption.createDecryptionCipher(agent.getFirstName());
        Cipher lastNameDecryptionCipher = Encryption.createDecryptionCipher(agent.getLastName());

        firstNameDecryptionCipher.doFinal(agent.getFirstName().getBytes());
        lastNameDecryptionCipher.doFinal(agent.getLastName().getBytes());

        return new Agent(firstNameDecryptionCipher.toString(), lastNameDecryptionCipher.toString());
    }


}
