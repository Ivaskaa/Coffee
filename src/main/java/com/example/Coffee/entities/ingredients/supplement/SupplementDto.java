package com.example.Coffee.entities.ingredients.supplement;

import com.example.Coffee.entities.ingredients.milk.Milk;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SupplementDto {
    private Long id;
    private String name;
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
