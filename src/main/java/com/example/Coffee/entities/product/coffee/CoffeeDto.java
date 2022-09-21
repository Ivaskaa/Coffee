package com.example.Coffee.entities.product.coffee;

import lombok.Data;

import java.util.Set;

@Data
public class CoffeeDto {
    private Long id;
    private String name;
    private String photo;
    private String description;
    private boolean active;
    private Set<CoffeeSize> sizes;

    public final Coffee build(){
        Coffee coffee = new Coffee();
        coffee.setId(this.id);
        coffee.setName(this.name);
        coffee.setDescription(this.description);
        coffee.setPhoto(this.photo);
        coffee.setSizes(this.sizes);
        coffee.setActive(this.active);
        return coffee;
    }
}
