package com.example.Coffee.controllers.ingredients;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.ingredients.alcohol.AlcoholDto;
import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.entities.ingredients.milk.MilkDto;
import com.example.Coffee.service.ingredient.MilkService;
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
public class MilkController {
    private final MilkService milkService;
    private final ObjectMapper mapper;


    @GetMapping("/milk")
    public String milk(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "ingredients/milk";
    }

    @GetMapping("/getAllActiveMilks")
    @ResponseBody
    public String getAllActiveMilks() throws JsonProcessingException {
        return mapper.writeValueAsString(milkService.findAllActive());
    }

    @GetMapping("/getMilks")
    @ResponseBody
    public String getMilks(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(milkService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addMilk")
    @ResponseBody
    public String addMilk(
            @RequestBody @Valid MilkDto milkDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        milkService.save(milkDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateMilk")
    @ResponseBody
    public String updateMilk(
            @RequestBody @Valid MilkDto milkDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        milkService.update(milkDto.getId(), milkDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteMilkById")
    @ResponseBody
    public String deleteMilkById(
            Long id
    ) throws JsonProcessingException {
        milkService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getMilkById")
    @ResponseBody
    public String getMilkById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(milkService.findById(id));
    }
}
