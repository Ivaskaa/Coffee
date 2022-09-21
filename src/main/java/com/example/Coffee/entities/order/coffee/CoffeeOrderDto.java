package com.example.Coffee.entities.order.coffee;

import com.example.Coffee.entities.ingredients.alcohol.AlcoholDto;
import com.example.Coffee.entities.ingredients.milk.MilkDto;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.entities.ingredients.syrup.SyrupDto;
import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.entities.product.coffee.CoffeeSize;
import lombok.Data;

@Data
public class CoffeeOrderDto {
    private Long id;
    private CoffeeDto coffee;
    private SyrupDto syrup;
    private AlcoholDto alcohol;
    private MilkDto milk;
    private SupplementDto supplement;
    private CoffeeSize size;
    private Integer count;
    private boolean active;

    public final CoffeeOrder build(){
        CoffeeOrder coffeeOrder = new CoffeeOrder();
        coffeeOrder.setId(this.id);
        if(this.coffee != null){
            coffeeOrder.setCoffee(this.coffee.build());
        }
        coffeeOrder.setSize(this.size);
        if(this.syrup != null) {
            coffeeOrder.setSyrup(this.syrup.build());
        }
        if(this.milk != null) {
            coffeeOrder.setMilk(this.milk.build());
        }
        if(this.alcohol != null) {
            coffeeOrder.setAlcohol(this.alcohol.build());
        }
        if(this.supplement != null) {
            coffeeOrder.setSupplement(this.supplement.build());
        }
        coffeeOrder.setCount(this.count);
        coffeeOrder.setActive(this.active);
        return coffeeOrder;
    }
}
