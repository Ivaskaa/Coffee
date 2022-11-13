package com.example.Coffee.entities.order.dessert;

import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.entities.product.dessert.DessertSize;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DessertOrderDto {
    private Long id;
    @NotNull(message = "Must not be empty")
    private Long dessertId;
    private Long syrupId;
    private Long supplementId;
    @NotNull(message = "Must not be empty")
    private Long sizeId;
    @NotNull(message = "Must not be empty")
    private Integer count;
    private boolean active;

    public final DessertOrder build(){
        DessertOrder order = new DessertOrder();
        order.setId(id);
        if(dessertId != null){
            Dessert dessert = new Dessert();
            dessert.setId(dessertId);
            order.setDessert(dessert);
        }
        if(syrupId != null) {
            Syrup syrup = new Syrup();
            syrup.setId(syrupId);
            order.setSyrup(syrup);
        }
        if(supplementId != null) {
            Supplement supplement = new Supplement();
            supplement.setId(supplementId);
            order.setSupplement(supplement);
        }
        if(sizeId != null) {
            DessertSize size = new DessertSize();
            size.setId(sizeId);
            order.setSize(size);
        }
        order.setCount(count);
        order.setActive(active);
        return order;
    }
}
