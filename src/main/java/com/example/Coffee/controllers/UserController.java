package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Location;
import com.example.Coffee.entities.user.User;
import com.example.Coffee.entities.user.UserDto;
import com.example.Coffee.service.UserService;
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
            @RequestBody @Valid UserDto userDto,
            BindingResult bindingResult
    ) throws IOException {
        User user = userService.findById(userDto.getId());
        if(!user.getPhone().equals(userDto.getPhone())){
            if (userService.checkUnicPhone(userDto.getPhone())) {
                bindingResult.addError(new FieldError("userDto", "phone", "This phone is already exist"));
            }
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        userService.update(userDto.getId(), userDto.build());
        return mapper.writeValueAsString(null);
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
