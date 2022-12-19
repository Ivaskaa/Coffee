package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.service.product.CoffeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CoffeeController {
    private final CoffeeService coffeeService;
    private final ObjectMapper mapper;
    @GetMapping( "/coffee" )
    public String coffee(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "products/coffee";
    }

    @GetMapping("/getAllActiveCoffees")
    @ResponseBody
    public String getAllActiveCoffees(Long id) throws JsonProcessingException {
        return mapper.writeValueAsString(coffeeService.findAllActive(id));
    }

    @GetMapping("/getCoffees")
    @ResponseBody
    public String getCoffees(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(coffeeService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addCoffee")
    @ResponseBody
    public String addCoffee(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("coffee") @Valid CoffeeDto coffeeDto,
            BindingResult bindingResult
    ) throws IOException {
        //validation
        coffeeService.coffeeSizesValidation(coffeeDto, bindingResult);
        if (file == null || file.isEmpty()){
            bindingResult.addError(new FieldError("coffeeDto", "photo", "Must not be empty"));
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        coffeeService.save(coffeeDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateCoffee")
    @ResponseBody
    public String updateCoffee(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("coffee") @Valid CoffeeDto coffeeDto,
            BindingResult bindingResult
    ) throws IOException {
        coffeeService.coffeeSizesValidation(coffeeDto, bindingResult);
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        coffeeService.update(coffeeDto.getId(), coffeeDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteCoffeeById")
    @ResponseBody
    public String deleteCoffeeById(
            Long id
    ) throws JsonProcessingException, FileNotFoundException {
        coffeeService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getCoffeeById")
    @ResponseBody
    public String getCoffeeById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(coffeeService.findById(id));
    }
}
