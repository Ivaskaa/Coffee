package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.PrivacyPolicy;
import com.example.Coffee.repository.PrivacyPoliciesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class PrivacyPoliciesController {
    private final ObjectMapper mapper;
    private final PrivacyPoliciesRepository policiesRepository;

    @GetMapping( "/privacyPolicy" )
    public String privacyPolicies(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "privacyPolicy";
    }

    @GetMapping("/getPrivacyPolicy")
    @ResponseBody
    public String getPrivacyPolicies() throws JsonProcessingException {
        return mapper.writeValueAsString(policiesRepository.findById(1L));
    }

    @PostMapping("/updatePrivacyPolicy")
    @ResponseBody
    public String updatePrivacyPolicies(
            String text
    ) throws JsonProcessingException {
        PrivacyPolicy privacyPolicy = policiesRepository.findById(1L).orElseThrow();
        privacyPolicy.setText(text);
        policiesRepository.save(privacyPolicy);
        return mapper.writeValueAsString("success");
    }
}
