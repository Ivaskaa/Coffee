package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.AdminDto;
import com.example.Coffee.service.AdminService;
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
public class AdminController {

    private final AdminService adminService;
    private final ObjectMapper mapper;

    @GetMapping( "/admin" )
    public String admin(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "admin";
    }

    @GetMapping("/getAdmins")
    @ResponseBody
    public String getAdmins(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(adminService.findSortingPage(page, field, direction));
    }

    @PostMapping("/addAdmin")
    @ResponseBody
    public String addAlcohol(
            @Valid @RequestBody AdminDto adminDto,
            BindingResult bindingResult
    ) throws IOException {
        System.out.println("hello");
        //validation
        if(adminService.findUserByUsername(adminDto.getUsername()) != null){
            bindingResult.addError(new FieldError("admin", "username", "This username is already exist"));
        }
        if(adminDto.getPassword() == null || adminDto.getPassword().equals("")){
            bindingResult.addError(new FieldError("admin", "password", "Must not be empty"));
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            System.out.println(errors);
            return mapper.writeValueAsString(errors);
        }
        //action
        adminService.saveAdmin(adminDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateAdmin")
    @ResponseBody
    public String updateAdmin(
            @Valid @RequestBody AdminDto adminDto,
            BindingResult bindingResult
    ) throws IOException {
        //validation
        Admin adminFromDb = adminService.findById(adminDto.getId());
        if (!adminFromDb.getUsername().equals(adminDto.getUsername())){
            if(adminService.loadUserByUsername(adminDto.getUsername()) != null){
                bindingResult.addError(new FieldError("admin", "username", "This username is already exist"));
            }
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        //action
        adminService.updateAdmin(adminDto.getId(), adminDto.build());
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteAdminById")
    @ResponseBody
    public String deleteAdminById(
            @AuthenticationPrincipal Admin admin,
            Long id
    ) throws JsonProcessingException {
        if(admin.getId().equals(id)){
            return mapper.writeValueAsString("Warning, you can`t delete yourself");
        }
        adminService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getAdminById")
    @ResponseBody
    public String getAdminById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(adminService.findById(id));
    }

    @GetMapping("/checkUniqueUsername")
    @ResponseBody
    public String checkUniqueUsername(
            @RequestParam("username") String username
    ) throws JsonProcessingException {
        try {
            adminService.loadUserByUsername(username);
        } catch (Exception e){
            return mapper.writeValueAsString("success");
        }
        return mapper.writeValueAsString("error");
    }
}
