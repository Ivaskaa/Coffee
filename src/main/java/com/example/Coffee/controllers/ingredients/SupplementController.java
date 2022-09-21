package com.example.Coffee.controllers.ingredients;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.service.ingredient.SupplementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

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
            Supplement supplement
    ) throws IOException {
        supplementService.save(supplement);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateSupplement")
    @ResponseBody
    public String updateSupplement(
            Supplement supplement
    ) throws IOException {
        supplementService.update(supplement.getId(), supplement);
        return mapper.writeValueAsString("success");
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
