package com.example.Coffee.entities.ingredients.sauce;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class SauceDto {
    private Long id;
    @NotBlank(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    @Pattern(regexp = ".*[a-zA-Z]{3,}.*", message = "Must contain 3 letters")
    private String name;
    @NotNull(message = "Must not be empty")
    @DecimalMin(value = "0.01", message = "Must be greater then 0.01")
    @DecimalMax(value = "1000.0", message = "Must be less then 1000")
    private BigDecimal price;
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
