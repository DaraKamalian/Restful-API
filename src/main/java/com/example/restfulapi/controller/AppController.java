package com.example.restfulapi.controller;

import com.example.restfulapi.domain.Agent;
import com.example.restfulapi.domain.EncryptionKey;
import com.example.restfulapi.service.AgentService;
import com.example.restfulapi.service.EncryptAgent;
import com.example.restfulapi.service.Encryption;
import com.example.restfulapi.service.EncryptionKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class AppController {
    @Autowired
    private AgentService agentService;

    @Autowired
    private EncryptionKeyService encryptionKeyService;

    private EncryptionKey cipherKey;

    @RequestMapping("/")
    public String viewHomePage(Model model) throws NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidKeyException {
        List<Agent> cipherAgents = agentService.getAllAgents();
        for(Agent cipherAgent: cipherAgents){
           EncryptAgent.decryptAgent(cipherAgent);
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
            InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        SecretKey secretKey = EncryptAgent.createKey(agent);
        Agent cipherAgent = EncryptAgent.encryptAgent(agent);
        agentService.save(cipherAgent);
        return "redirect:/";
    }


    @RequestMapping("/delete/{id}")
    public String deleteAgent(@PathVariable(name = "id") Long id) {
        agentService.delete(id);
        return "redirect:/ ";
    }

}
