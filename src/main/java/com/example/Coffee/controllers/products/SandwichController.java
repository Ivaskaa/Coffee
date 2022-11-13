package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.sandwich.SandwichDto;
import com.example.Coffee.service.product.SandwichService;
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
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("sandwich") @Valid SandwichDto sandwichDto,
            BindingResult bindingResult
    ) throws IOException {
        //validation
        if (file == null || file.isEmpty()){
            bindingResult.addError(new FieldError("sandwichDto", "photo", "Must not be empty"));
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        sandwichService.save(sandwichDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateSandwich")
    @ResponseBody
    public String updateSandwich(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("sandwich") @Valid SandwichDto sandwichDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        sandwichService.update(sandwichDto.getId(), sandwichDto.build(), file);
        return mapper.writeValueAsString(null);
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
