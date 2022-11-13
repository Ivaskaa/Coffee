package com.example.Coffee.controllers.awards;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.order.snack.SnackOrderDto;
import com.example.Coffee.entities.order.tea.TeaOrderDto;
import com.example.Coffee.service.order.TeaOrderService;
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
            @RequestBody @Valid TeaOrderDto teaOrderDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        teaOrderService.save(teaOrderDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateTeaOrder")
    @ResponseBody
    public String updateTeaOrder(
            @RequestBody @Valid TeaOrderDto teaOrderDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        teaOrderService.update(teaOrderDto.build());
        return mapper.writeValueAsString(null);
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
