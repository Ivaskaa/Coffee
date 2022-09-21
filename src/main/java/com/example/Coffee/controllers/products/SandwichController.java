package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.sandwich.SandwichDto;
import com.example.Coffee.service.product.SandwichService;
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
public class SandwichController {
    private final SandwichService sandwichService;
    private final ObjectMapper mapper;

    @GetMapping( "/sandwich" )
    public String sandwich(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "products/sandwich";
    }

    @GetMapping("/getAllActiveSandwiches")
    @ResponseBody
    public String getAllActiveSandwiches() throws JsonProcessingException {
        return mapper.writeValueAsString(sandwichService.findAllActive());
    }

    @GetMapping("/getSandwiches")
    @ResponseBody
    public String getSandwiches(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(sandwichService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addSandwich")
    @ResponseBody
    public String addSandwich(
            @RequestParam("sandwich") String sandwichJsonString,
            MultipartFile file
    ) throws IOException {
        SandwichDto sandwichDto = mapper.readValue(sandwichJsonString, SandwichDto.class);
        sandwichService.save(sandwichDto.build(), file);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateSandwich")
    @ResponseBody
    public String updateSandwich(
            @RequestParam("sandwich") String sandwichJsonString,
            MultipartFile file,
            Long id
    ) throws IOException {
        SandwichDto sandwichDto = mapper.readValue(sandwichJsonString, SandwichDto.class);
        sandwichService.update(id, sandwichDto.build(), file);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/deleteSandwichById")
    @ResponseBody
    public String deleteSandwichById(
            Long id
    ) throws JsonProcessingException, FileNotFoundException {
        sandwichService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getSandwichById")
    @ResponseBody
    public String getSandwichById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(sandwichService.findById(id));
    }
}
