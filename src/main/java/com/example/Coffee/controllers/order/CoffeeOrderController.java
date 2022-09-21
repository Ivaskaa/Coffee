package com.example.Coffee.controllers.order;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.service.order.CoffeeOrderService;
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


@Controller
@AllArgsConstructor
public class CoffeeOrderController {
    private final CoffeeOrderService coffeeOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/coffeeOrder" )
    public String coffeeOrder(
            @AuthenticationPrincipal Admin admin,
            @RequestParam("orderId") Long orderId,
            Model model
    ){
        model.addAttribute("orderId", orderId);
        model.addAttribute("admin", admin);
        return "orders/coffeeOrder";
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

    @PostMapping("/deleteCoffeeOrderById")
    @ResponseBody
    public String deleteCoffeeOrderById(
            Long id
    ) throws JsonProcessingException {
        coffeeOrderService.deleteById(id);
        return mapper.writeValueAsString("success");
    }
}
