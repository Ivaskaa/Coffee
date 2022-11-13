package com.example.Coffee.controllers.ingredients;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.entities.ingredients.alcohol.AlcoholDto;
import com.example.Coffee.entities.product.dessert.DessertDto;
import com.example.Coffee.service.ingredient.AlcoholService;
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
@AllArgsConstructor
public class AlcoholController {
    private final AlcoholService alcoholService;
    private final ObjectMapper mapper;

    @GetMapping("/alcohol")
    public String alcohol(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "ingredients/alcohol";
    }

    @GetMapping("/getAllActiveAlcohols")
    @ResponseBody
    public String getAllActiveAlcohols() throws JsonProcessingException {
        return mapper.writeValueAsString(alcoholService.findAllActive());
    }

    @GetMapping("/getAlcohols")
    @ResponseBody
    public String getAlcohols(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(alcoholService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addAlcohol")
    @ResponseBody
    public String addAlcohol(
            @RequestBody @Valid AlcoholDto alcoholDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        alcoholService.save(alcoholDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateAlcohol")
    @ResponseBody
    public String updateAlcohol(
            @RequestBody @Valid AlcoholDto alcoholDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        alcoholService.update(alcoholDto.getId(), alcoholDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteAlcoholById")
    @ResponseBody
    public String deleteAlcoholById(
            Long id
    ) throws JsonProcessingException {
        alcoholService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getAlcoholById")
    @ResponseBody
    public String getAlcoholById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(alcoholService.findById(id));
    }
}
