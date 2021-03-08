package com.example.restfulapi.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "EncryptionKey")
public class EncryptionKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public EncryptionKey(Agent agent) {
        this.agent = agent;
    }

    public EncryptionKey() {
    }

    public EncryptionKey(int value, Agent agent) {
        this.value = value;
        this.agent = agent;
    }
}
