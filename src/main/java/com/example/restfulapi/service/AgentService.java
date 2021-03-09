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




}
