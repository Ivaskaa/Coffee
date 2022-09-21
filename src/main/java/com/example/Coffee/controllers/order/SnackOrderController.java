package com.example.Coffee.controllers.order;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.service.order.SnackOrderService;
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
public class SnackOrderController {

    private final SnackOrderService snackOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/snackOrder" )
    public String snackOrder(
            @AuthenticationPrincipal Admin admin,
            @RequestParam("orderId") Long orderId,
            Model model
    ){
        model.addAttribute("orderId", orderId);
        model.addAttribute("admin", admin);
        return "orders/snackOrder";
    }

    @GetMapping("/getSnackOrders")
    @ResponseBody
    public String getSnackOrders(
            Integer page,
            String field,
            String direction,
            Long orderId
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(snackOrderService.findPageByOrderId(
                page,
                field,
                direction,
                orderId));
    }

    @PostMapping("/deleteSnackOrderById")
    @ResponseBody
    public String deleteSnackOrderById(
            Long id
    ) throws JsonProcessingException {
        snackOrderService.deleteById(id);
        return mapper.writeValueAsString("success");
    }
}
