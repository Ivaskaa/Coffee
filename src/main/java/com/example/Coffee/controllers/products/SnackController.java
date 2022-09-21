package com.example.Coffee.controllers.products;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.entities.product.snack.SnackDto;
import com.example.Coffee.service.product.SnackService;
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
            @RequestParam("snack") String snackJsonString,
            MultipartFile file
    ) throws IOException {
        SnackDto snackDto = mapper.readValue(snackJsonString, SnackDto.class);
        snackService.save(snackDto.build(), file);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateSnack")
    @ResponseBody
    public String updateSnack(
            @RequestParam("snack") String snackJsonString,
            MultipartFile file,
            Long id
    ) throws IOException {
        SnackDto snackDto = mapper.readValue(snackJsonString, SnackDto.class);
        snackService.update(id, snackDto.build(), file);
        return mapper.writeValueAsString("success");
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
