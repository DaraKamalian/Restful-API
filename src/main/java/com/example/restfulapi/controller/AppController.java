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
    public String viewHomePage(Model model) {
        List<Agent> agents = agentService.getAllAgents();
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
    public String saveAgent(@ModelAttribute("agent") Agent agent) {
        EncryptionKey key = new EncryptionKey(new Random().nextInt(100) + 1, agent);

        agentService.save(agent);
        String cipherAgentFirstName = Encryption.encrypt(agent.getFirstName(), key);
        String cipherAgentLastName = Encryption.encrypt(agent.getLastName(), key);

        agent.setFirstName(cipherAgentFirstName);
        agent.setLastName(cipherAgentLastName);
        agent.setDateAdded(LocalDate.now());
        agent.setEncryptionKeys(Arrays.asList(key));
//        Agent cipherAgent = new Agent();
//        cipherAgent.setFirstName(cipherAgentFirstName);
//        cipherAgent.setLastName(cipherAgentLastName);
//        cipherAgent.setDateAdded(LocalDate.now());
//        cipherAgent.setEncryptionKeys(Arrays.asList(key));

        agentService.save(agent);
        return "redirect:/";
    }

    @RequestMapping(value = "/generateNewKey")
    public EncryptionKey generateNewKey(@ModelAttribute("agent") Agent agent) {
        return agentService.generateNewKey(agent);
    }

    @RequestMapping("/delete/{id}")
    public String deleteAgent(@PathVariable(name = "id") Long id) {
        agentService.delete(id);
        return "redirect:/ ";
    }

}
