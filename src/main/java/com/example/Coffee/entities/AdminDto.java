package com.example.Coffee.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AdminDto {
    private Long id;
    @NotBlank(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String username;
    @Size(max = 255, message = "Must be less than 255 characters")
    private String password;
    private boolean active;

    public Admin build(){
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setActive(active);
        return admin;
    }
}
