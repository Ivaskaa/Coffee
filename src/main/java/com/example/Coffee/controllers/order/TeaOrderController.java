package com.example.Coffee.controllers.order;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.service.order.TeaOrderService;
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
public class TeaOrderController {

    private final TeaOrderService teaOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/teaOrder" )
    public String teaOrder(
            @AuthenticationPrincipal Admin admin,
            @RequestParam("orderId") Long orderId,
            Model model
    ){
        model.addAttribute("orderId", orderId);
        model.addAttribute("admin", admin);
        return "orders/teaOrder";
    }

    @GetMapping("/getTeaOrders")
    @ResponseBody
    public String getTeaOrders(
            Integer page,
            String field,
            String direction,
            Long orderId
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(teaOrderService.findPageByOrderId(
                page,
                field,
                direction,
                orderId));
    }

    @PostMapping("/deleteTeaOrderById")
    @ResponseBody
    public String deleteTeaOrderById(
            Long id
    ) throws JsonProcessingException {
        teaOrderService.deleteById(id);
        return mapper.writeValueAsString("success");
    }
}
