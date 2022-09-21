package com.example.Coffee.entities.ingredients.sauce;

import lombok.Data;

@Data
public class SauceDto {
    private Long id;
    private String name;
    private Double price;
    private boolean active;
    public final Sauce build(){
        Sauce sauce = new Sauce();
        sauce.setId(this.id);
        sauce.setName(this.name);
        sauce.setPrice(this.price);
        sauce.setActive(this.active);
        return sauce;
    }
}
