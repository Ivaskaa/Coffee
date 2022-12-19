package com.example.Coffee.entities.product.tea;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
public class TeaDto {
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
    private List<TeaSize> sizes;

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
