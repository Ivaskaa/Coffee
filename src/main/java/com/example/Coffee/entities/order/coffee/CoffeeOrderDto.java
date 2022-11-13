package com.example.Coffee.entities.order.coffee;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.entities.product.coffee.CoffeeSize;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CoffeeOrderDto {
    private Long id;
    @NotNull(message = "Must not be empty")
    private Long coffeeId;
    private Long syrupId;
    private Long alcoholId;
    private Long milkId;
    private Long supplementId;
    @NotNull(message = "Must not be empty")
    private Long sizeId;
    @NotNull(message = "Must be valid")
    private Integer count;
    private boolean active;

    public final CoffeeOrder build(){
        CoffeeOrder order = new CoffeeOrder();
        order.setId(id);
        if(coffeeId != null){
            Coffee coffee = new Coffee();
            coffee.setId(coffeeId);
            order.setCoffee(coffee);
        }
        if(syrupId != null) {
            Syrup syrup = new Syrup();
            syrup.setId(syrupId);
            order.setSyrup(syrup);
        }
        if(alcoholId!= null) {
            Alcohol alcohol = new Alcohol();
            alcohol.setId(alcoholId);
            order.setAlcohol(alcohol);
        }
        if(milkId != null) {
            Milk milk = new Milk();
            milk.setId(milkId);
            order.setMilk(milk);
        }
        if(supplementId != null) {
            Supplement supplement = new Supplement();
            supplement.setId(supplementId);
            order.setSupplement(supplement);
        }
        if(sizeId != null) {
            CoffeeSize size = new CoffeeSize();
            size.setId(sizeId);
            order.setSize(size);
        }
        order.setCount(count);
        order.setActive(active);
        return order;
    }
}
