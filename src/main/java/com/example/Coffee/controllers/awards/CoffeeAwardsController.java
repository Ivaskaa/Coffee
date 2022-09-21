package com.example.Coffee.controllers.awards;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.order.coffee.CoffeeOrderDto;
import com.example.Coffee.service.order.CoffeeOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/awards")
@AllArgsConstructor
public class CoffeeAwardsController {
    private final CoffeeOrderService coffeeOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/coffee" )
    public String coffee(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("orderId", 1);
        model.addAttribute("admin", admin);
        return "awards/coffee";
    }

    @GetMapping("/getCoffeeOrders")
    @ResponseBody
    public String getCoffeeOrders(
            Integer page,
            String field,
            String direction,
            Long orderId
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(coffeeOrderService.findPageByOrderId(
                page,
                field,
                direction,
                orderId));
    }

    @PostMapping("/addCoffeeOrder")
    @ResponseBody
    public String addCoffeeOrder(
            @RequestBody CoffeeOrderDto coffeeOrderDto  // @RequestBody for json
    ) throws IOException {
        coffeeOrderService.save(coffeeOrderDto.build());
        return mapper.writeValueAsString("success");
    }


    @PostMapping("/updateCoffeeOrder")
    @ResponseBody
    public String updateCoffeeOrder(
            @RequestBody CoffeeOrderDto coffeeOrderDto
    ) throws IOException {
        coffeeOrderService.update(coffeeOrderDto.build());
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getCoffeeOrderById")
    @ResponseBody
    public String getCoffeeOrderById(Long id) throws JsonProcessingException {
        return mapper.writeValueAsString(coffeeOrderService.findById(id));
    }

    @PostMapping("/deleteCoffeeOrderById")
    @ResponseBody
    public String deleteCoffeeOrderById(
            Long id
    ) throws JsonProcessingException {
        coffeeOrderService.deleteById(id);
        return mapper.writeValueAsString("success");
    }
}
