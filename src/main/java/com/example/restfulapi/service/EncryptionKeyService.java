package com.example.restfulapi.service;

import com.example.restfulapi.domain.EncryptionKey;
import com.example.restfulapi.repository.EncryptionKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EncryptionKeyService {
    @Autowired
    private EncryptionKeyRepository encryptionKeyRepository;

    public List<EncryptionKey> getAllEncryptionKeys() {
        return encryptionKeyRepository.findAll();
    }

    public void save(EncryptionKey encryptionKey) {
        encryptionKeyRepository.save(encryptionKey);
    }

    public EncryptionKey get(Long id) {
        return encryptionKeyRepository.findById(id).get();
    }

    public void delete(Long id) {
        encryptionKeyRepository.deleteById(id);
    }
}
