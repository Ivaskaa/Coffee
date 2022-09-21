package com.example.Coffee.others;

import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.order.dessert.DessertOrder;
import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.entities.order.snack.SnackOrder;
import com.example.Coffee.entities.order.tea.TeaOrder;
import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.example.Coffee.entities.product.dessert.DessertSize;
import com.example.Coffee.entities.product.sandwich.SandwichSize;
import com.example.Coffee.entities.product.snack.SnackSize;
import com.example.Coffee.entities.product.tea.TeaSize;
import com.example.Coffee.service.order.TeaOrderService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderFactory {
    private final ProductFactory productFactory;
    private final IngredientsFactory ingredientsFactory;

    public CoffeeOrder getCoffeeOrder(){
        CoffeeOrder order = new CoffeeOrder();
        order.setCoffee(productFactory.getCoffee());
        order.setAlcohol(ingredientsFactory.getAlcohol());
        order.setMilk(ingredientsFactory.getMilk());
        order.setSupplement(ingredientsFactory.getSupplement());
        order.setSyrup(ingredientsFactory.getSyrup());
        order.setSize(new CoffeeSize());
        order.setCount(1);
        order.setActive(true);
        return order;
    }
    public DessertOrder getDessertOrder(){
        DessertOrder order = new DessertOrder();
        order.setDessert(productFactory.getDessert());
        order.setSupplement(ingredientsFactory.getSupplement());
        order.setSyrup(ingredientsFactory.getSyrup());
        order.setSize(new DessertSize());
        order.setCount(1);
        order.setActive(true);
        return order;
    }

    public SandwichOrder getSandwichOrder(){
        SandwichOrder order = new SandwichOrder();
        order.setSandwich(productFactory.getSandwich());
        order.setSupplement(ingredientsFactory.getSupplement());
        order.setSauce(ingredientsFactory.getSauce());
        order.setSize(new SandwichSize());
        order.setCount(1);
        order.setActive(true);
        return order;
    }

    public SnackOrder getSnackOrder(){
        SnackOrder order = new SnackOrder();
        order.setSnack(productFactory.getSnack());
        order.setSupplement(ingredientsFactory.getSupplement());
        order.setSauce(ingredientsFactory.getSauce());
        order.setSize(new SnackSize());
        order.setCount(1);
        order.setActive(true);
        return order;
    }

    public TeaOrder getTeaOrder(){
        TeaOrder order = new TeaOrder();
        order.setTea(productFactory.getTea());
        order.setSupplement(ingredientsFactory.getSupplement());
        order.setMilk(ingredientsFactory.getMilk());
        order.setSyrup(ingredientsFactory.getSyrup());
        order.setSize(new TeaSize());
        order.setCount(1);
        order.setActive(true);
        return order;
    }
}
