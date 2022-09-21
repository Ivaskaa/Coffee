package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.service.SliderImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@AllArgsConstructor
@Slf4j
public class SliderImageController {
    private final SliderImageService sliderImageService;
    private final ObjectMapper mapper;

    @GetMapping( "/sliderImage" )
    public String sliderImage(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "sliderImage";
    }

    @GetMapping("/getSliderImages")
    @ResponseBody
    public String getSliderImages() throws JsonProcessingException {
        return mapper.writeValueAsString(sliderImageService.findAll());
    }

    @PostMapping("/addSliderImage")
    @ResponseBody
    public String addSliderImage(
            MultipartFile file
    ) throws IOException {
        sliderImageService.save(file);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/deleteSliderImageById")
    @ResponseBody
    public String deleteCoffeeById(
            Long id
    ) throws JsonProcessingException, FileNotFoundException {
        sliderImageService.deleteById(id);
        return mapper.writeValueAsString("success");
    }
}
