package com.example.Coffee.controllers.awards;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.order.coffee.CoffeeOrderDto;
import com.example.Coffee.entities.order.dessert.DessertOrderDto;
import com.example.Coffee.service.order.DessertOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/awards")
@AllArgsConstructor
public class DessertAwardsController {
    private final DessertOrderService dessertOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/dessert" )
    public String dessert(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("orderId", 1);
        model.addAttribute("admin", admin);
        return "awards/dessert";
    }

    @GetMapping("/getDessertOrders")
    @ResponseBody
    public String getDessertOrders(
            Integer page,
            String field,
            String direction,
            Long orderId
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(dessertOrderService.findPageByOrderId(
                page,
                field,
                direction,
                orderId));
    }

    @PostMapping("/addDessertOrder")
    @ResponseBody
    public String addDessertOrder(
            @RequestBody @Valid DessertOrderDto dessertOrderDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        dessertOrderService.save(dessertOrderDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateDessertOrder")
    @ResponseBody
    public String updateDessertOrder(
            @RequestBody @Valid DessertOrderDto dessertOrderDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        dessertOrderService.update(dessertOrderDto.build());
        return mapper.writeValueAsString(null);
    }

    @GetMapping("/getDessertOrderById")
    @ResponseBody
    public String getDessertOrderById(Long id) throws JsonProcessingException {
        return mapper.writeValueAsString(dessertOrderService.findById(id));
    }

    @PostMapping("/deleteDessertOrderById")
    @ResponseBody
    public String deleteDessertOrderById(
            Long id
    ) throws JsonProcessingException {
        dessertOrderService.deleteById(id);
        return mapper.writeValueAsString("success");
    }
}
