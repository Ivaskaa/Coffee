package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.service.product.CoffeeService;
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
    public String getAllActiveCoffees() throws JsonProcessingException {
        return mapper.writeValueAsString(coffeeService.findAllActive());
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
            @RequestParam("coffee") String coffeeJsonString,
            MultipartFile file
    ) throws IOException {
        CoffeeDto coffeeDto = mapper.readValue(coffeeJsonString, CoffeeDto.class);
        coffeeService.save(coffeeDto.build(), file);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateCoffee")
    @ResponseBody
    public String updateCoffee(
            @RequestParam("coffee") String coffeeJsonString,
            MultipartFile file,
            Long id
    ) throws IOException {
        CoffeeDto coffeeDto = mapper.readValue(coffeeJsonString, CoffeeDto.class);
        coffeeService.update(id, coffeeDto.build(), file);
        return mapper.writeValueAsString("success");
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
