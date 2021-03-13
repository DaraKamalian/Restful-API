package com.example.restfulapi.domain;

import javax.persistence.*;
import java.security.Key;
import java.time.LocalDate;

@Entity
@Table(name = "EncryptionKey")
public class EncryptionKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public EncryptionKey(Agent agent) {
        this.agent = agent;
    }

    public EncryptionKey() {
    }

    public EncryptionKey(byte[] value, Agent agent) {
        this.value = value;
        this.agent = agent;
    }

    public EncryptionKey(byte[] value) {
        this.value = value;
    }


    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
