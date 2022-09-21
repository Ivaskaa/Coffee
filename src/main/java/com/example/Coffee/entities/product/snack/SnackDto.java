package com.example.Coffee.entities.product.snack;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SnackDto {
    private Long id;
    private String name;
    private String photo;
    private String description;
    private boolean active;
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
