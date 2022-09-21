package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.service.AdminService;
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

import java.io.IOException;

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
            Admin admin
    ) throws IOException {
        adminService.saveAdmin(admin);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateAdmin")
    @ResponseBody
    public String updateAdmin(
            Admin admin
    ) throws IOException {
        adminService.updateAdmin(admin.getId(), admin);
        return mapper.writeValueAsString("success");
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
