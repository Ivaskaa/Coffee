package com.example.Coffee.controllers.awards;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.order.sandwich.SandwichOrderDto;
import com.example.Coffee.service.order.SandwichOrderService;
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
public class SandwichAwardsController {
    private final SandwichOrderService sandwichOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/sandwich" )
    public String sandwich(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("orderId", 1);
        model.addAttribute("admin", admin);
        return "awards/sandwich";
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

    @PostMapping("/addSandwichOrder")
    @ResponseBody
    public String addSandwichOrder(
            @RequestBody SandwichOrderDto sandwichOrderDto  // @RequestBody for json
    ) throws IOException {
        sandwichOrderService.save(sandwichOrderDto.build());
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateSandwichOrder")
    @ResponseBody
    public String updateSandwichOrder(
            @RequestBody SandwichOrderDto sandwichOrderDto
    ) throws IOException {
        sandwichOrderService.update(sandwichOrderDto.build());
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getSandwichOrderById")
    @ResponseBody
    public String getSandwichOrderById(Long id) throws JsonProcessingException {
        return mapper.writeValueAsString(sandwichOrderService.findById(id));
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
