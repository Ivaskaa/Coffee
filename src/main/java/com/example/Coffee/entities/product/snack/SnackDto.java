package com.example.Coffee.entities.product.snack;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
public class SnackDto {
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
    private List<SnackSize> sizes;

    public final Snack build(){
        Snack snack = new Snack();
        snack.setId(this.id);
        snack.setName(this.name);
        snack.setDescription(this.description);
        snack.setPhoto(this.photo);
        snack.setSizes(this.sizes);
        snack.setActive(this.active);
        return snack;
    }
}
