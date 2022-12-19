package com.example.Coffee.entities.product.dessert;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
public class DessertDto {
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
    private List<DessertSize> sizes;

    public final Dessert build(){
        Dessert dessert = new Dessert();
        dessert.setId(this.id);
        dessert.setName(this.name);
        dessert.setDescription(this.description);
        dessert.setPhoto(this.photo);
        dessert.setSizes(this.sizes);
        dessert.setActive(this.active);
        return dessert;
    }
}
