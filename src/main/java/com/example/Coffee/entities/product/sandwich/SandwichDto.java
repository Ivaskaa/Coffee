package com.example.Coffee.entities.product.sandwich;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class SandwichDto {
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
    private List<SandwichSize> sizes;

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
