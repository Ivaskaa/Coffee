package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Education;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.service.EducationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class EducationController {
    private final EducationService educationService;
    private final ObjectMapper mapper;

    @GetMapping("/education")
    public String education(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "education";
    }

    @GetMapping("/getEducations")
    @ResponseBody
    public String getEducations(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(educationService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addEducation")
    @ResponseBody
    public String addEducation(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @Valid @RequestPart("education") Education education,
            BindingResult bindingResult
    ) throws IOException {
        System.out.println("hello");
        if (file == null || file.isEmpty()){
            bindingResult.addError(new FieldError("education", "photo", "Must not be empty"));
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        educationService.save(education, file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateEducation")
    @ResponseBody
    public String updateEducation(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("education") @Valid Education education,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        educationService.update(education.getId(), education, file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteEducationById")
    @ResponseBody
    public String deleteEducationById(
            Long id
    ) throws JsonProcessingException, FileNotFoundException {
        educationService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getEducationById")
    @ResponseBody
    public String getEducationById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(educationService.findById(id));
    }
}
