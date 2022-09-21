package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Education;
import com.example.Coffee.service.EducationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

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
            @RequestParam("education") String educationJsonString,
            MultipartFile file
    ) throws IOException {
        Education education = mapper.readValue(educationJsonString, Education.class);
        educationService.save(education, file);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateEducation")
    @ResponseBody
    public String updateEducation(
            @RequestParam("education") String educationJsonString,
            MultipartFile file,
            Long id
    ) throws IOException {
        Education education = mapper.readValue(educationJsonString, Education.class);
        educationService.update(id, education, file);
        return mapper.writeValueAsString("success");
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
