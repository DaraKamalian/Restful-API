package com.example.restfulapi.controller;

import com.example.restfulapi.repository.AgentRepository;
import com.example.restfulapi.service.EncryptAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.management.AgentConfigurationError;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@Slf4j
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    private AgentRepository agentRepository;
   @Autowired
    private EncryptAgentService encryptAgentService;

   @GetMapping("/")
   public List agents() {
       return StreamSupport.stream(agentRepository.findAll().spliterator(), false).collect(Collectors.toList());
   }

}
