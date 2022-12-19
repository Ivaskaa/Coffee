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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
            @Valid @RequestBody PrivacyPolicy privacyPolicy,
            BindingResult bindingResult
    ) throws JsonProcessingException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        PrivacyPolicy privacyPolicy2 = policiesRepository.findById(1L).orElseThrow();
        privacyPolicy2.setText(privacyPolicy.getText());
        policiesRepository.save(privacyPolicy2);
        return mapper.writeValueAsString(null);
    }
}
