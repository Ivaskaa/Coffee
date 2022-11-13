package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.city.City;
import com.example.Coffee.entities.city.CityDto;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.service.CityService;
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
public class CityController {
    private CityService cityService;
    private ObjectMapper mapper;

    @GetMapping( "/city" )
    public String city(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "city";
    }

    @GetMapping("/getCities")
    @ResponseBody
    public String getCities(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(cityService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addCity")
    @ResponseBody
    public String addCity(
            @RequestBody @Valid CityDto cityDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        cityService.save(cityDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateCity")
    @ResponseBody
    public String updateCity(
            @RequestBody @Valid CityDto cityDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        cityService.update(cityDto.getId(), cityDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteCityById")
    @ResponseBody
    public String deleteCityById(
            Long id
    ) throws JsonProcessingException {
        cityService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getCityById")
    @ResponseBody
    public String getCityById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(cityService.findById(id));
    }

}
