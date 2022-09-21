package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.entities.product.dessert.DessertDto;
import com.example.Coffee.service.product.DessertService;
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
            @RequestParam("dessert") String dessertJsonString,
            MultipartFile file
    ) throws IOException {
        DessertDto dessertDto = mapper.readValue(dessertJsonString, DessertDto.class);
        dessertService.save(dessertDto.build(), file);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateDessert")
    @ResponseBody
    public String updateDessert(
            @RequestParam("dessert") String dessertJsonString,
            MultipartFile file,
            Long id
    ) throws IOException {
        DessertDto dessertDto = mapper.readValue(dessertJsonString, DessertDto.class);
        dessertService.update(id, dessertDto.build(), file);
        return mapper.writeValueAsString("success");
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
