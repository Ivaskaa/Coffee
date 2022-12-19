package com.example.Coffee.entities.city;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CityDto {
    private Long id;
    @NotBlank(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
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
