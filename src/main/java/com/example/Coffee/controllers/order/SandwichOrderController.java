package com.example.Coffee.controllers.order;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.service.order.SandwichOrderService;
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
public class SandwichOrderController {
    private final SandwichOrderService sandwichOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/sandwichOrder" )
    public String sandwichOrder(
            @AuthenticationPrincipal Admin admin,
            @RequestParam("orderId") Long orderId,
            Model model
    ){
        model.addAttribute("orderId", orderId);
        model.addAttribute("admin", admin);
        return "orders/sandwichOrder";
    }

    @GetMapping("/getSandwichOrders")
    @ResponseBody
    public String getSandwichOrders(
            Integer page,
            String field,
            String direction,
            Long orderId
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(sandwichOrderService.findPageByOrderId(
                page,
                field,
                direction,
                orderId));
    }

    @PostMapping("/deleteSandwichOrderById")
    @ResponseBody
    public String deleteSandwichOrderById(
            Long id
    ) throws JsonProcessingException {
        sandwichOrderService.deleteById(id);
        return mapper.writeValueAsString("success");
    }
}
