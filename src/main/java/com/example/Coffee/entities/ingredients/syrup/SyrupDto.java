package com.example.Coffee.entities.ingredients.syrup;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SyrupDto {
    private Long id;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotNull(message = "Must be valid")
    private Double price;
    private boolean active;

    public final Syrup build(){
        Syrup syrup = new Syrup();
        syrup.setId(this.id);
        syrup.setName(this.name);
        syrup.setPrice(this.price);
        syrup.setActive(this.active);
        return syrup;
    }
}
