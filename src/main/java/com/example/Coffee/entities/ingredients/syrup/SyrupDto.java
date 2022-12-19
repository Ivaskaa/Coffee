package com.example.Coffee.entities.ingredients.syrup;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class SyrupDto {
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

    public final Syrup build(){
        Syrup syrup = new Syrup();
        syrup.setId(this.id);
        syrup.setName(this.name);
        syrup.setPrice(this.price);
        syrup.setActive(this.active);
        return syrup;
    }
}
