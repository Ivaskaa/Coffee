package com.example.Coffee.entities.product.coffee;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
public class CoffeeDto {
    private Long id;
    @NotBlank(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    @Pattern(regexp = ".*[a-zA-Z]{3,}.*", message = "Must contain 3 letters")
    private String name;
    private String photo;
    @NotBlank(message = "Must not be empty")
    @Size(max = 5000, message = "Must be less than 5000 characters")
    private String description;
    private boolean active;
    @NotEmpty(message = "Must not be empty")
    private List<CoffeeSize> sizes;

    public final Coffee build(){
        Coffee coffee = new Coffee();
        coffee.setId(this.id);
        coffee.setName(this.name);
        coffee.setDescription(this.description);
        coffee.setPhoto(this.photo);
        coffee.setSizes(this.sizes);
        coffee.setActive(this.active);
        return coffee;
    }
}
