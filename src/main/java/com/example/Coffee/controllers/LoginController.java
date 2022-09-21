package com.example.Coffee.controllers;


import com.example.Coffee.entities.Admin;
import com.example.Coffee.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("adminForm", new Admin());
        return "login";
    }

    @PostMapping("/login")
    public String loginButton(
            @ModelAttribute("adminForm") Admin adminForm,
            BindingResult bindingResult,
            Model model)
    {
        if(adminForm.getUsername().isEmpty()){
            model.addAttribute("msg", "Phone is empty");
            return "login";
        }
        if(adminForm.getPassword().isEmpty()){
            model.addAttribute("msg", "Password is empty");
            return "login";
        }
        if(bindingResult.hasErrors()) {
            return "login";
        }
        Admin admin = adminService.loadUserByUsername(adminForm.getUsername());

        if(!admin.getUsername().equals(adminForm.getUsername())){ // FIXME: 21.08.2022
            model.addAttribute("msg", "Entered login is not registered");
            return "login";
        }

        return "user/user";
    }

//    @PostMapping("/login")
//    public String addUser(
//            @ModelAttribute("userForm") User userForm,
//            BindingResult bindingResult,
//            Model model
//    ) {
//        if(userForm == null){
//            return "registration";
//        }
//        User user = (User) userService.loadUserByUsername(userForm.getUsername());
//        if(user == null){
//            return "registration";
//        }
//        if(bindingResult.hasErrors()) {
//            return "registration";
//        }
//        if(!userForm.getPassword().equals(user.getPassword())){
//            return "registration";
//        }
//
//        return "redirect:/";
//    }
}
