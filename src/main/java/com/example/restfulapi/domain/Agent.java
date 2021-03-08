package com.example.restfulapi.domain;

import com.example.restfulapi.controller.*;
import com.example.restfulapi.domain.EncryptionKey;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
//@Table(name = "Agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateAdded;

    @OneToMany(
            mappedBy = "agent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EncryptionKey> encryptionKeys;

    public static String cipher(String string) {
        return string + "2";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Agent() {
    }


    public Agent(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Agent(String firstName, String lastName, LocalDate dateAdded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateAdded = dateAdded;
    }

    public Agent(String firstName, String lastName, LocalDate dateAdded, List<EncryptionKey> encryptionKeys) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateAdded = dateAdded;
        this.encryptionKeys = encryptionKeys;
    }

    public List<EncryptionKey> getEncryptionKeys() {
        return encryptionKeys;
    }

    public void setEncryptionKeys(List<EncryptionKey> encryptionKeys) {
        this.encryptionKeys = encryptionKeys;
    }
}
