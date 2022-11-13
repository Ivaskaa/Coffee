package com.example.Coffee.entities.ingredients.alcohol;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AlcoholDto {
    private Long id;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotNull(message = "Must be valid")
    private Double price;
    private boolean active;

    public final Alcohol build(){
        Alcohol alcohol = new Alcohol();
        alcohol.setId(this.id);
        alcohol.setName(this.name);
        alcohol.setPrice(this.price);
        alcohol.setActive(this.active);
        return alcohol;
    }
}
