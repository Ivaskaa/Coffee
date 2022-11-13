package com.example.Coffee.entities.ingredients.milk;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MilkDto {
    private Long id;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotNull(message = "Must be valid")
    private Double price;
    private boolean active;
    public final Milk build(){
        Milk milk = new Milk();
        milk.setId(this.id);
        milk.setName(this.name);
        milk.setPrice(this.price);
        milk.setActive(this.active);
        return milk;
    }
}
