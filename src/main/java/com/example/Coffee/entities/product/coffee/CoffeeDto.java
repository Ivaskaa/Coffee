package com.example.Coffee.entities.product.coffee;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class CoffeeDto {
    private Long id;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    private String photo;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String description;
    private boolean active;
    @NotNull(message = "Must not be empty")
    private Set<CoffeeSize> sizes;

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
