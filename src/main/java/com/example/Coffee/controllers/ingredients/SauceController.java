package com.example.Coffee.controllers.ingredients;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.ingredients.alcohol.AlcoholDto;
import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.entities.ingredients.sauce.SauceDto;
import com.example.Coffee.service.ingredient.SauceService;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public String getAllActiveSauces(Long id) throws JsonProcessingException {
        return mapper.writeValueAsString(sauceService.findAllActive(id));
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
            @RequestBody @Valid SauceDto sauceDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        sauceService.save(sauceDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateSauce")
    @ResponseBody
    public String updateSauce(
            @RequestBody @Valid SauceDto sauceDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        sauceService.update(sauceDto.getId(), sauceDto.build());
        return mapper.writeValueAsString(null);
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
