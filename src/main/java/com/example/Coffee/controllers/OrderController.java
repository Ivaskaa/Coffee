package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ObjectMapper mapper;

    @GetMapping( "/order" )
    public String order(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "order";
    }

    @GetMapping("/getOrders")
    @ResponseBody
    public String getOrders(
            Integer page,
            String field,
            String direction,
            String id,
            String name,
            String dateStart,
            String dateFin,
            String timeStart,
            String timeFin,
            String city,
            String home,
            String entrance,
            String flat
    ) throws JsonProcessingException, ParseException {
        return mapper.writeValueAsString(orderService.findSortingAndSpecificationPage(
                page, field, direction,
                id,
                name,
                dateStart, dateFin,
                timeStart, timeFin,
                city,
                home,
                entrance,
                flat));
    }

    @GetMapping("/getOrderById")
    @ResponseBody
    public String getOrderById(
            Long id
    ) throws JsonProcessingException {
        Order order = orderService.findById(id);
        return mapper.writeValueAsString(order);
    }

    @PostMapping("/deleteOrderById")
    @ResponseBody
    public String deleteOrderById(
            Long id
    ) throws JsonProcessingException {
        orderService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

}
