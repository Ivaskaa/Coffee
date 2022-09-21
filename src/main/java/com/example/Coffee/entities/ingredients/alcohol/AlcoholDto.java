package com.example.Coffee.entities.ingredients.alcohol;

import lombok.Data;

@Data
public class AlcoholDto {
    private Long id;
    private String name;
    private Double price;
    private boolean active;

    public final Alcohol build(){
        Alcohol alcohol = new Alcohol();
        alcohol.setId(this.id);
        alcohol.setName(this.name);
        alcohol.setPrice(this.price);
        alcohol.setActive(this.active);
        return alcohol;
    }
}
