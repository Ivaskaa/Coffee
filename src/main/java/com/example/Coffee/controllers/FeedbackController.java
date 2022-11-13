package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Feedback;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.service.FeedbackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final ObjectMapper mapper;

    @GetMapping("/feedback")
    public String feedback(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "feedback";
    }

    @GetMapping("/getFeedbacks")
    @ResponseBody
    public String getFeedbacks(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(feedbackService.findSortingPage(page, field, direction));
    }

    @PostMapping("/updateFeedback")
    @ResponseBody
    public String updateFeedback(
            @RequestBody @Valid Feedback feedback,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        feedbackService.update(feedback.getId(), feedback);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteFeedbackById")
    @ResponseBody
    public String deleteFeedbackById(
            Long id
    ) throws JsonProcessingException {
        feedbackService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getFeedbackById")
    @ResponseBody
    public String getFeedbackById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(feedbackService.findById(id));
    }
}
