package com.example.Coffee.entities.product.dessert;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class DessertDto {
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
    private Set<DessertSize> sizes;

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
