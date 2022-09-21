package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.repository.FeedbackRepository;
import com.example.Coffee.repository.LocationRepository;
import com.example.Coffee.repository.OrderRepository;
import com.example.Coffee.repository.UserRepository;
import com.example.Coffee.repository.ingredients.*;
import com.example.Coffee.repository.orderDetails.*;
import com.example.Coffee.repository.product.*;
import com.example.Coffee.service.product.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DashboardController {
    private final OrderRepository orderRepository;
    private final FeedbackRepository feedbackRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    private final AlcoholRepository alcoholRepository;
    private final MilkRepository milkRepository;
    private final SauceRepository sauceRepository;
    private final SupplementRepository supplementRepository;
    private final SyrupRepository syrupRepository;

    private final CoffeeRepository coffeeRepository;
    private final TeaRepository teaRepository;
    private final SandwichRepository sandwichRepository;
    private final SnackRepository snackRepository;
    private final DessertRepository dessertRepository;

    private final CoffeeOrderRepository coffeeOrderRepository;
    private final TeaOrderRepository teaOrderRepository;
    private final SandwichOrderRepository sandwichOrderRepository;
    private final SnackOrderRepository snackOrderRepository;
    private final DessertOrderRepository dessertOrderRepository;

    @GetMapping("/")
    public String dashboard(
            Model model,
            @AuthenticationPrincipal Admin admin
    ) {
        model.addAttribute("admin", admin);
        model.addAttribute("countOrder", orderRepository.findCount()-1);
        model.addAttribute("countLocation", locationRepository.findCount());
        model.addAttribute("countFeedback", feedbackRepository.findCount());
        model.addAttribute("countUser", userRepository.findCount());


        model.addAttribute("countAlcohol", alcoholRepository.findCount());
        model.addAttribute("countMilk", milkRepository.findCount());
        model.addAttribute("countSauce", sauceRepository.findCount());
        model.addAttribute("countSupplement", supplementRepository.findCount());
        model.addAttribute("countSyrup", syrupRepository.findCount());

        model.addAttribute("countCoffee", coffeeRepository.findCount());
        model.addAttribute("countTea", teaRepository.findCount());
        model.addAttribute("countSandwich", sandwichRepository.findCount());
        model.addAttribute("countSnack", snackRepository.findCount());
        model.addAttribute("countDessert", dessertRepository.findCount());

        model.addAttribute("countCoffeeOrder", coffeeOrderRepository.findCount());
        model.addAttribute("countTeaOrder", teaOrderRepository.findCount());
        model.addAttribute("countSandwichOrder", sandwichOrderRepository.findCount());
        model.addAttribute("countSnackOrder", snackOrderRepository.findCount());
        model.addAttribute("countDessertOrder", dessertOrderRepository.findCount());
        return "dashboard";
    }
}
