package com.example.restfulapi.controller;

import com.example.restfulapi.domain.Agent;
import com.example.restfulapi.domain.EncryptionKey;
import com.example.restfulapi.service.AgentService;
import com.example.restfulapi.service.Encryption;
import com.example.restfulapi.service.EncryptionKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
        List<Agent> agents = agentService.getAllAgents();
        for(Agent agent: agents){
            agentService.decryptAgent(agent);
        }
        model.addAttribute("agents", agents);
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
        Agent cipherAgent = agentService.encryptAgent(agent);
        agentService.save(cipherAgent);
        return "redirect:/";
    }

//    @RequestMapping(value = "/generateNewKey")
//    public EncryptionKey generateNewKey(@ModelAttribute("agent") Agent agent) {
//        return agentService.generateNewKey(agent);
//    }

    @RequestMapping("/delete/{id}")
    public String deleteAgent(@PathVariable(name = "id") Long id) {
        agentService.delete(id);
        return "redirect:/ ";
    }

}
