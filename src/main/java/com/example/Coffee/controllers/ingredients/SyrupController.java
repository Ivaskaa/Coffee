package com.example.Coffee.controllers.ingredients;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.service.ingredient.SyrupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class SyrupController {
    private final SyrupService syrupService;
    private final ObjectMapper mapper;

    @GetMapping("/syrup")
    public String syrup(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "ingredients/syrup";
    }

    @GetMapping("/getAllActiveSyrups")
    @ResponseBody
    public String getAllActiveSyrups() throws JsonProcessingException {
        return mapper.writeValueAsString(syrupService.findAllActive());
    }

    @GetMapping("/getSyrups")
    @ResponseBody
    public String getSyrups(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(syrupService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addSyrup")
    @ResponseBody
    public String addSyrup(
            Syrup syrup
    ) throws IOException {
        syrupService.save(syrup);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateSyrup")
    @ResponseBody
    public String updateSyrup(
            Syrup syrup
    ) throws IOException {
        syrupService.update(syrup.getId(), syrup);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/deleteSyrupById")
    @ResponseBody
    public String deleteSyrupById(
            Long id
    ) throws JsonProcessingException {
        syrupService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getSyrupById")
    @ResponseBody
    public String getSyrupById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(syrupService.findById(id));
    }
}
