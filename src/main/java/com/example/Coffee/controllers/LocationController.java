package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Location;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.service.LocationService;
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
public class LocationController {
    private final LocationService locationService;
    private final ObjectMapper mapper;

    @GetMapping( "/location" )
    public String location(
            @AuthenticationPrincipal Admin admin,
            Model model
    ){
        model.addAttribute("admin", admin);
        return "location";
    }

    @GetMapping("/getLocations")
    @ResponseBody
    public String getLocations(
            Integer page,
            String field,
            String direction
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(locationService.findSortingPage(page, field, direction));
    }

    @GetMapping("/getAllActiveLocations")
    @ResponseBody
    public String getAllActiveLocations(Long id) throws JsonProcessingException {
        return mapper.writeValueAsString(locationService.findAllActive(id));
    }

    @PostMapping("/addLocation")
    @ResponseBody
    public String addLocation(
            @RequestBody @Valid Location location,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        locationService.save(location);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/updateLocation")
    @ResponseBody
    public String updateLocation(
            @RequestBody @Valid Location location,
            BindingResult bindingResult
    ) throws IOException {
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return mapper.writeValueAsString(errors);
        }
        locationService.update(location.getId(), location);
        return mapper.writeValueAsString(null);
    }

    @PostMapping("/deleteLocationById")
    @ResponseBody
    public String deleteLocationById(
            Long id
    ) throws JsonProcessingException {
        locationService.deleteById(id);
        return mapper.writeValueAsString("success");
    }

    @GetMapping("/getLocationById")
    @ResponseBody
    public String getLocationById(
            Long id
    ) throws JsonProcessingException {
        return mapper.writeValueAsString(locationService.findById(id));
    }
}
