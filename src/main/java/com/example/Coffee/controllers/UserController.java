package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.user.User;
import com.example.Coffee.entities.user.UserDto;
import com.example.Coffee.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final ObjectMapper mapper;

    @GetMapping( "/user" )
    public String user(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "user";
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public String getUsers(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(userService.findSortingPage(page, field, direction));
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public String updateUser(
            @RequestBody UserDto userDto
    ) throws IOException {
        System.out.println(userDto);
        userService.update(userDto.getId(), userDto.build());
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/deleteUserById")
    @ResponseBody
    public String deleteUserById(
            Long id
    ) throws JsonProcessingException {
        userService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getUserById")
    @ResponseBody
    public String getUserById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(userService.findById(id));
    }

    @GetMapping("/checkUniquePhone")
    @ResponseBody
    public String checkUniquePhone(
            @RequestParam("phone") String phone
    ) throws JsonProcessingException {
        User user;
        try {
            user = userService.loadUserByPhone(phone);
        } catch (Exception e){
            return mapper.writeValueAsString("success");
        }
        return mapper.writeValueAsString(user);
    }
}
