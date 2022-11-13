package com.example.Coffee.controllers.awards;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.order.coffee.CoffeeOrderDto;
import com.example.Coffee.entities.order.snack.SnackOrderDto;
import com.example.Coffee.service.order.SnackOrderService;
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
public class SnackAwardsController {
    private final SnackOrderService snackOrderService;
    private final ObjectMapper mapper;

    @GetMapping( "/snack" )
    public String snack(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("orderId", 1);
        model.addAttribute("admin", admin);
        return "awards/snack";
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

    @PostMapping("/addSnackOrder")
    @ResponseBody
    public String addSnackOrder(
            @RequestBody @Valid SnackOrderDto snackOrderDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        snackOrderService.save(snackOrderDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateSnackOrder")
    @ResponseBody
    public String updateSnackOrder(
            @RequestBody @Valid SnackOrderDto snackOrderDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        snackOrderService.update(snackOrderDto.build());
        return mapper.writeValueAsString(null);
    }

    @GetMapping("/getSnackOrderById")
    @ResponseBody
    public String getSnackOrderById(Long id) throws JsonProcessingException {
        return mapper.writeValueAsString(snackOrderService.findById(id));
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
