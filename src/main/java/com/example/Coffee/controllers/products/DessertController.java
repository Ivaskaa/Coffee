package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.entities.product.dessert.DessertDto;
import com.example.Coffee.service.product.DessertService;
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
public class DessertController {
    private final DessertService dessertService;
    private final ObjectMapper mapper;

    @GetMapping( "/dessert" )
    public String dessert(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "products/dessert";
    }

    @GetMapping("/getAllActiveDesserts")
    @ResponseBody
    public String getCoffees() throws JsonProcessingException {
        return mapper.writeValueAsString(dessertService.findAllActive());
    }

    @GetMapping("/getDesserts")
    @ResponseBody
    public String getDesserts(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(dessertService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addDessert")
    @ResponseBody
    public String addDessert(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("dessert") @Valid DessertDto dessertDto,
            BindingResult bindingResult
    ) throws IOException {
        //validation
        if (file == null || file.isEmpty()){
            bindingResult.addError(new FieldError("dessertDto", "photo", "Must not be empty"));
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        dessertService.save(dessertDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateDessert")
    @ResponseBody
    public String updateDessert(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("dessert") @Valid DessertDto dessertDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        dessertService.update(dessertDto.getId(), dessertDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteDessertById")
    @ResponseBody
    public String deleteDessertById(
            Long id
    ) throws JsonProcessingException, FileNotFoundException {
        dessertService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getDessertById")
    @ResponseBody
    public String getDessertById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(dessertService.findById(id));
    }
}
