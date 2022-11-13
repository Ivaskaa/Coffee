package com.example.Coffee.entities.product.snack;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SnackDto {
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
    private Set<SnackSize> sizes;

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
