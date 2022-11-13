package com.example.Coffee.entities.ingredients.supplement;

import com.example.Coffee.entities.ingredients.milk.Milk;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SupplementDto {
    private Long id;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotNull(message = "Must be valid")
    private Double price;
    private boolean active;

    public final Supplement build(){
        Supplement supplement = new Supplement();
        supplement.setId(this.id);
        supplement.setName(this.name);
        supplement.setPrice(this.price);
        supplement.setActive(this.active);
        return supplement;
    }
}
