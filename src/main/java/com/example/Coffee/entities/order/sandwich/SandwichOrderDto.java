package com.example.Coffee.entities.order.sandwich;

import com.example.Coffee.entities.ingredients.sauce.SauceDto;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.entities.product.sandwich.SandwichDto;
import com.example.Coffee.entities.product.sandwich.SandwichSize;
import lombok.Data;

@Data
public class SandwichOrderDto {
    private Long id;
    private SandwichDto sandwich;
    private SandwichSize size;
    private SauceDto sauce;
    private SupplementDto supplement;
    private Integer count;
    private boolean active;

    public final SandwichOrder build(){
        SandwichOrder sandwichOrder = new SandwichOrder();
        sandwichOrder.setId(this.id);
        if(this.sandwich != null) {
            sandwichOrder.setSandwich(this.sandwich.build());
        }
        sandwichOrder.setSize(this.size);
        if(this.sauce != null) {
            sandwichOrder.setSauce(this.sauce.build());
        }
        if(this.supplement != null) {
            sandwichOrder.setSupplement(this.supplement.build());
        }
        sandwichOrder.setCount(this.count);
        sandwichOrder.setActive(this.active);
        return sandwichOrder;
    }
}
