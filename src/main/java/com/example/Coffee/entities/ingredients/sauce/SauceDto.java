package com.example.Coffee.entities.ingredients.sauce;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SauceDto {
    private Long id;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotNull(message = "Must be valid")
    private Double price;
    private boolean active;
    public final Sauce build(){
        Sauce sauce = new Sauce();
        sauce.setId(this.id);
        sauce.setName(this.name);
        sauce.setPrice(this.price);
        sauce.setActive(this.active);
        return sauce;
    }
}
