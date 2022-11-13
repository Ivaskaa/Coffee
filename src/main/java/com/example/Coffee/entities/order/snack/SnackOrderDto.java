package com.example.Coffee.entities.order.snack;

import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.entities.ingredients.sauce.SauceDto;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.sandwich.SandwichSize;
import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.entities.product.snack.SnackDto;
import com.example.Coffee.entities.product.snack.SnackSize;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SnackOrderDto {
    private Long id;
    @NotNull(message = "Must not be empty")
    private Long snackId;
    private Long sauceId;
    private Long supplementId;
    @NotNull(message = "Must not be empty")
    private Long sizeId;
    @NotNull(message = "Must not be empty")
    private Integer count;
    private boolean active;

    public final SnackOrder build(){
        SnackOrder order = new SnackOrder();
        order.setId(id);
        if(snackId != null){
            Snack snack = new Snack();
            snack.setId(snackId);
            order.setSnack(snack);
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
            SnackSize size = new SnackSize();
            size.setId(sizeId);
            order.setSize(size);
        }
        order.setCount(count);
        order.setActive(active);
        return order;
    }
}
