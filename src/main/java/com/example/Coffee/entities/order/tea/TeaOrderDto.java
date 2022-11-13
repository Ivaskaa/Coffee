package com.example.Coffee.entities.order.tea;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.entities.ingredients.milk.MilkDto;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.supplement.SupplementDto;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.entities.ingredients.syrup.SyrupDto;
import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.order.tea.TeaOrder;
import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.example.Coffee.entities.product.tea.Tea;
import com.example.Coffee.entities.product.tea.TeaDto;
import com.example.Coffee.entities.product.tea.TeaSize;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeaOrderDto {
    private Long id;
    @NotNull(message = "Must not be empty")
    private Long teaId;
    private Long syrupId;
    private Long milkId;
    private Long supplementId;
    @NotNull(message = "Must not be empty")
    private Long sizeId;
    @NotNull(message = "Must not be empty")
    private Integer count;
    private boolean active;

    public final TeaOrder build(){
        TeaOrder order = new TeaOrder();
        order.setId(id);
        if(teaId != null){
            Tea tea = new Tea();
            tea.setId(teaId);
            order.setTea(tea);
        }
        if(syrupId != null) {
            Syrup syrup = new Syrup();
            syrup.setId(syrupId);
            order.setSyrup(syrup);
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
            TeaSize size = new TeaSize();
            size.setId(sizeId);
            order.setSize(size);
        }
        order.setCount(count);
        order.setActive(active);
        return order;
    }
}
