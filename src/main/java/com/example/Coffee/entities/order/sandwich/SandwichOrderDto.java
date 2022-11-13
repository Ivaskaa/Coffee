package com.example.Coffee.entities.order.sandwich;

import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.entities.ingredients.sauce.SauceDto;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.entities.order.dessert.DessertOrder;
import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.entities.product.dessert.DessertSize;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.sandwich.SandwichDto;
import com.example.Coffee.entities.product.sandwich.SandwichSize;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SandwichOrderDto {
    private Long id;
    @NotNull(message = "Must not be empty")
    private Long sandwichId;
    private Long sauceId;
    private Long supplementId;
    @NotNull(message = "Must not be empty")
    private Long sizeId;
    @NotNull(message = "Must not be empty")
    private Integer count;
    private boolean active;

    public final SandwichOrder build(){
        SandwichOrder order = new SandwichOrder();
        order.setId(id);
        if(sandwichId != null){
            Sandwich sandwich = new Sandwich();
            sandwich.setId(sandwichId);
            order.setSandwich(sandwich);
        }
        if(sauceId != null) {
            Sauce sauce = new Sauce();
            sauce.setId(sauceId);
            order.setSauce(sauce);
        }
        if(supplementId != null) {
            Supplement supplement = new Supplement();
            supplement.setId(supplementId);
            order.setSupplement(supplement);
        }
        if(sizeId != null) {
            SandwichSize size = new SandwichSize();
            size.setId(sizeId);
            order.setSize(size);
        }
        order.setCount(count);
        order.setActive(active);
        return order;
    }
}
