package com.example.Coffee.controllers.ingredients;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.ingredients.alcohol.AlcoholDto;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.service.ingredient.SupplementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class SupplementController {
    private final SupplementService supplementService;
    private final ObjectMapper mapper;

    @GetMapping("/supplement")
    public String supplement(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "ingredients/supplement";
    }

    @GetMapping("/getAllActiveSupplements")
    @ResponseBody
    public String getAllActiveSupplements() throws JsonProcessingException {
        return mapper.writeValueAsString(supplementService.findAllActive());
    }

    @GetMapping("/getSupplements")
    @ResponseBody
    public String getSupplements(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(supplementService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addSupplement")
    @ResponseBody
    public String addSupplement(
            @RequestBody @Valid SupplementDto supplementDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        supplementService.save(supplementDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateSupplement")
    @ResponseBody
    public String updateSupplement(
            @RequestBody @Valid SupplementDto supplementDto,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        supplementService.update(supplementDto.getId(), supplementDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteSupplementById")
    @ResponseBody
    public String deleteSupplementById(
            Long id
    ) throws JsonProcessingException {
        supplementService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getSupplementById")
    @ResponseBody
    public String getSupplementById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(supplementService.findById(id));
    }
}
