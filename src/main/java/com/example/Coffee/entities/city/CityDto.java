package com.example.Coffee.entities.city;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CityDto {
    private Long id;
    @NotEmpty(message = "Must not be empty")
    private String name;
    private boolean active;

    public final City build(){
        City city = new City();
        city.setId(this.id);
        city.setName(this.name);
        city.setActive(this.active);
        return city;
    }
}
