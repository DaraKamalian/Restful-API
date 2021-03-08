package com.example.restfulapi.repository;

import com.example.restfulapi.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
