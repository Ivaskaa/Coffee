package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.tea.Tea;
import com.example.Coffee.entities.product.tea.TeaDto;
import com.example.Coffee.service.product.TeaService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

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
            @RequestParam("tea") String teaJsonString,
            MultipartFile file
    ) throws IOException {
        TeaDto teaDto = mapper.readValue(teaJsonString, TeaDto.class);
        teaService.save(teaDto.build(), file);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateTea")
    @ResponseBody
    public String updateTea(
            @RequestParam("tea") String teaJsonString,
            MultipartFile file,
            Long id
    ) throws IOException {
        TeaDto teaDto = mapper.readValue(teaJsonString, TeaDto.class);
        teaService.update(id, teaDto.build(), file);
        return mapper.writeValueAsString("success");
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
