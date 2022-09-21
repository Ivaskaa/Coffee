package com.example.Coffee.controllers.ingredients;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.service.ingredient.AlcoholService;
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
            Alcohol alcohol
    ) throws IOException {
        alcoholService.save(alcohol);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateAlcohol")
    @ResponseBody
    public String updateAlcohol(
            Alcohol alcohol
    ) throws IOException {
        alcoholService.update(alcohol.getId(), alcohol);
        return mapper.writeValueAsString("success");
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
