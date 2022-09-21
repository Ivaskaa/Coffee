package com.example.Coffee.controllers.ingredients;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.service.ingredient.SauceService;
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
public class SauceController {
    private final SauceService sauceService;
    private final ObjectMapper mapper;

    @GetMapping("/sauce")
    public String sauce(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "ingredients/sauce";
    }

    @GetMapping("/getAllActiveSauces")
    @ResponseBody
    public String getAllActiveSauces() throws JsonProcessingException {
        return mapper.writeValueAsString(sauceService.findAllActive());
    }

    @GetMapping("/getSauces")
    @ResponseBody
    public String getSauces(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(sauceService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addSauce")
    @ResponseBody
    public String addSauce(
            Sauce sauce
    ) throws IOException {
        sauceService.save(sauce);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateSauce")
    @ResponseBody
    public String updateSauce(
            Sauce sauce
    ) throws IOException {
        sauceService.update(sauce.getId(), sauce);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/deleteSauceById")
    @ResponseBody
    public String deleteSauceById(
            Long id
    ) throws JsonProcessingException {
        sauceService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getSauceById")
    @ResponseBody
    public String getSauceById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(sauceService.findById(id));
    }

}
