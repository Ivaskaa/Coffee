package com.example.Coffee.entities.product.sandwich;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SandwichDto {
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
