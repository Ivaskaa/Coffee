package com.example.Coffee.entities.product.tea;

import com.example.Coffee.entities.product.coffee.Coffee;
import lombok.Data;

import java.util.Set;

@Data
public class TeaDto {
    private Long id;
    private String name;
    private String photo;
    private String description;
    private boolean active;
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
