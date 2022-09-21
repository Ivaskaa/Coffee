package com.example.Coffee.controllers.order;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.service.order.DessertOrderService;
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
public class DessertOrderController {

    private final DessertOrderService dessertOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/dessertOrder" )
    public String dessertOrder(
            @AuthenticationPrincipal Admin admin,
            @RequestParam("orderId") Long orderId,
            Model model
    ){
        model.addAttribute("orderId", orderId);
        model.addAttribute("admin", admin);
        return "orders/dessertOrder";
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

    @PostMapping("/deleteDessertOrderById")
    @ResponseBody
    public String deleteDessertOrderById(
            Long id
    ) throws JsonProcessingException {
        dessertOrderService.deleteById(id);
        return mapper.writeValueAsString("success");
    }
}
