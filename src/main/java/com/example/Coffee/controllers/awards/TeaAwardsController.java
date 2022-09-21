package com.example.Coffee.controllers.awards;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.order.tea.TeaOrderDto;
import com.example.Coffee.service.order.TeaOrderService;
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
public class TeaAwardsController {
    private final TeaOrderService teaOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/tea" )
    public String tea(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("orderId", 1);
        model.addAttribute("admin", admin);
        return "awards/tea";
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

    @PostMapping("/addTeaOrder")
    @ResponseBody
    public String addTeaOrder(
            @RequestBody TeaOrderDto teaOrderDto  // @RequestBody for json
    ) throws IOException {
        teaOrderService.save(teaOrderDto.build());
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateTeaOrder")
    @ResponseBody
    public String updateTeaOrder(
            @RequestBody TeaOrderDto teaOrderDto
    ) throws IOException {
        teaOrderService.update(teaOrderDto.build());
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getTeaOrderById")
    @ResponseBody
    public String getTeaOrderById(Long id) throws JsonProcessingException {
        return mapper.writeValueAsString(teaOrderService.findById(id));
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
