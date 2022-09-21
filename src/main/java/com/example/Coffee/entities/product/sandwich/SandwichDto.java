package com.example.Coffee.entities.product.sandwich;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import java.util.Set;

@Data
public class SandwichDto {
    private Long id;
    private String name;
    private String photo;
    private String description;
    private boolean active;
    private Set<SandwichSize> sizes;

    public final Sandwich build(){
        Sandwich sandwich = new Sandwich();
        sandwich.setId(this.id);
        sandwich.setName(this.name);
        sandwich.setDescription(this.description);
        sandwich.setPhoto(this.photo);
        sandwich.setSizes(this.sizes);
        sandwich.setActive(this.active);
        return sandwich;
    }
}
