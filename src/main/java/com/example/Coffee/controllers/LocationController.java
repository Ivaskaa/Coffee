package com.example.Coffee.controllers;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Location;
import com.example.Coffee.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

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
    public String getAllActiveLocations() throws JsonProcessingException {
        return mapper.writeValueAsString(locationService.findAllActive());
    }

    @PostMapping("/addLocation")
    @ResponseBody
    public String addLocation(
            Location location
    ) throws IOException {
        locationService.save(location);
        return mapper.writeValueAsString("success");
    }

    @PostMapping("/updateLocation")
    @ResponseBody
    public String updateLocation(
            Location location
    ) throws IOException {
        System.out.println(location);
        locationService.update(location.getId(), location);
        return mapper.writeValueAsString("success");
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
