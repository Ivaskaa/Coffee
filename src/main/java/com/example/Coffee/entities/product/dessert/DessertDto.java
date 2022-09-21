package com.example.Coffee.entities.product.dessert;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import java.util.Set;

@Data
public class DessertDto {
    private Long id;
    private String name;
    private String photo;
    private String description;
    private boolean active;
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
