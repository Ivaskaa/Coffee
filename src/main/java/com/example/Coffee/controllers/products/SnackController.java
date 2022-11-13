package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.entities.product.snack.SnackDto;
import com.example.Coffee.service.product.SnackService;
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
public class SnackController {
    private final SnackService snackService;
    private final ObjectMapper mapper;

    @GetMapping( "/snack" )
    public String snack(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "products/snack";
    }

    @GetMapping("/getAllActiveSnacks")
    @ResponseBody
    public String getAllActiveSnacks() throws JsonProcessingException {
        return mapper.writeValueAsString(snackService.findAllActive());
    }

    @GetMapping("/getSnacks")
    @ResponseBody
    public String getSnacks(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(snackService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addSnack")
    @ResponseBody
    public String addSnack(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("snack") @Valid SnackDto snackDto,
            BindingResult bindingResult
    ) throws IOException {
        //validation
        if (file == null || file.isEmpty()){
            bindingResult.addError(new FieldError("snackDto", "photo", "Must not be empty"));
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        snackService.save(snackDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateSnack")
    @ResponseBody
    public String updateSnack(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("snack") @Valid SnackDto snackDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        snackService.update(snackDto.getId(), snackDto.build(), file);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteSnackById")
    @ResponseBody
    public String deleteSnackById(
            Long id
    ) throws JsonProcessingException, FileNotFoundException {
        snackService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getSnackById")
    @ResponseBody
    public String getSnackById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(snackService.findById(id));
    }
}
