package com.example.restfulapi.controller;

import com.example.restfulapi.domain.Agent;
import com.example.restfulapi.domain.EncryptionKey;
import com.example.restfulapi.service.AgentService;
import com.example.restfulapi.service.AgentCryptographyService;
import com.example.restfulapi.service.EncryptionKeyService;
import com.example.restfulapi.service.KeyGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class AppController {
    @Autowired
    private AgentService agentService;

    @Autowired
    private EncryptionKeyService encryptionKeyService;

    @Autowired
    private AgentCryptographyService agentCryptographyService;


    @RequestMapping("/")
    public String viewHomePage(Model model) throws NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            IOException, InvalidKeyException {

        List<Agent> cipherAgents = agentService.getAllAgents();
        for (Agent cipherAgent : cipherAgents) {
            cipherAgent.setFirstName(agentCryptographyService.decrypt(cipherAgent.getFirstName()));
            cipherAgent.setLastName(agentCryptographyService.decrypt(cipherAgent.getLastName()));
        }
        model.addAttribute("agents", cipherAgents);
        return "home";
    }

    @RequestMapping("/addNew")
    public String showNewAgentForm(Model model) {
        Agent agent = new Agent();
        model.addAttribute("agent", agent);

        return "add-agent";
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Agent getAgent(@PathVariable(name = "id") Long id) {
        return agentService.get(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveAgent(@ModelAttribute("agent") Agent agent) throws NoSuchPaddingException,
            InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {

        Key key = KeyGeneration.generateNewKey();

        String cipherAgentFirstname = agentCryptographyService.encrypt(agent.getFirstName(), key);
        String cipherAgentLastname = agentCryptographyService.encrypt(agent.getLastName(), key);

        EncryptionKey encryptionKey = new EncryptionKey(key.getEncoded());
        Agent cipherAgent = new Agent(cipherAgentFirstname, cipherAgentLastname, LocalDate.now(),
                Arrays.asList(encryptionKey));


        agentService.save(cipherAgent);
        encryptionKeyService.save(encryptionKey);
        return "redirect:/";
    }


    @RequestMapping("/delete/{id}")
    public String deleteAgent(@PathVariable(name = "id") Long id) {
        agentService.delete(id);
        return "redirect:/ ";
    }

}
