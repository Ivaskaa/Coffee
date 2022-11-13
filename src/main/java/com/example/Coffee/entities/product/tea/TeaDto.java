package com.example.Coffee.entities.product.tea;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class TeaDto {
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
    private Set<TeaSize> sizes;

    public final Tea build(){
        Tea tea = new Tea();
        tea.setId(this.id);
        tea.setName(this.name);
        tea.setDescription(this.description);
        tea.setPhoto(this.photo);
        tea.setSizes(this.sizes);
        tea.setActive(this.active);
        return tea;
    }
}
