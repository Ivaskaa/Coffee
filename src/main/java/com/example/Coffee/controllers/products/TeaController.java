package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.entities.product.tea.Tea;
import com.example.Coffee.entities.product.tea.TeaDto;
import com.example.Coffee.service.product.TeaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class TeaController {
    private final TeaService teaService;
    private final ObjectMapper mapper;

    @GetMapping( "/tea" )
    public String tea(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "products/tea";
    }

    @GetMapping("/getAllActiveTeas")
    @ResponseBody
    public String getAllActiveTeas() throws JsonProcessingException {
        return mapper.writeValueAsString(teaService.findAllActive());
    }

    @GetMapping("/getTeas")
    @ResponseBody
    public String getTeas(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(teaService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addTea")
    @ResponseBody
    public String addTea(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("tea") @Valid TeaDto teaDto,
            BindingResult bindingResult
    ) throws IOException {
        //validation
        if (file == null || file.isEmpty()){
            bindingResult.addError(new FieldError("coffeeDto", "photo", "Must not be empty"));
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        teaService.save(teaDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateTea")
    @ResponseBody
    public String updateTea(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("tea") @Valid TeaDto teaDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        teaService.update(teaDto.getId(), teaDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteTeaById")
    @ResponseBody
    public String deleteTeaById(
            Long id
    ) throws JsonProcessingException, FileNotFoundException {
        teaService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getTeaById")
    @ResponseBody
    public String getTeaById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(teaService.findById(id));
    }

}
