package com.example.restfulapi.repository;

import com.example.restfulapi.domain.EncryptionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncryptionKeyRepository extends JpaRepository<EncryptionKey, Long> {
}
