package com.example.Coffee.others;

import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.entities.product.dessert.DessertSize;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.sandwich.SandwichSize;
import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.entities.product.snack.SnackSize;
import com.example.Coffee.entities.product.tea.Tea;
import com.example.Coffee.entities.product.tea.TeaSize;

import java.util.HashSet;
import java.util.Set;

public class ProductFactory {
    public Coffee getCoffee(){
        Coffee product = new Coffee();
        product.setName("name");
        product.setDescription("desc");
        product.setSizes(getCoffeeSizes());
        product.setPhoto("photo.png");
        product.setActive(true);
        return product;
    }
    private Set<CoffeeSize> getCoffeeSizes(){
        CoffeeSize size = new CoffeeSize();
        size.setName("name");
        size.setDescription("desc");
        size.setPrice(1d);
        Set<CoffeeSize> sizes = new HashSet<>();
        sizes.add(size);
        return sizes;
    }

    public Dessert getDessert(){
        Dessert product = new Dessert();
        product.setName("name");
        product.setDescription("desc");
        product.setSizes(getDessertSizes());
        product.setPhoto("photo.png");
        product.setActive(true);
        return product;
    }
    private Set<DessertSize> getDessertSizes(){
        DessertSize size = new DessertSize();
        size.setName("name");
        size.setDescription("desc");
        size.setPrice(1d);
        Set<DessertSize> sizes = new HashSet<>();
        sizes.add(size);
        return sizes;
    }

    public Sandwich getSandwich(){
        Sandwich product = new Sandwich();
        product.setName("name");
        product.setDescription("desc");
        product.setSizes(getSandwichSizes());
        product.setPhoto("photo.png");
        product.setActive(true);
        return product;
    }
    private Set<SandwichSize> getSandwichSizes(){
        SandwichSize size = new SandwichSize();
        size.setName("name");
        size.setDescription("desc");
        size.setPrice(1d);
        Set<SandwichSize> sizes = new HashSet<>();
        sizes.add(size);
        return sizes;
    }

    public Snack getSnack(){
        Snack product = new Snack();
        product.setName("name");
        product.setDescription("desc");
        product.setSizes(getSnackSizes());
        product.setPhoto("photo.png");
        product.setActive(true);
        return product;
    }
    private Set<SnackSize> getSnackSizes(){
        SnackSize size = new SnackSize();
        size.setName("name");
        size.setDescription("desc");
        size.setPrice(1d);
        Set<SnackSize> sizes = new HashSet<>();
        sizes.add(size);
        return sizes;
    }

    public Tea getTea(){
        Tea product = new Tea();
        product.setName("name");
        product.setDescription("desc");
        product.setSizes(getTeaSizes());
        product.setPhoto("photo.png");
        product.setActive(true);
        return product;
    }
    private Set<TeaSize> getTeaSizes(){
        TeaSize size = new TeaSize();
        size.setName("name");
        size.setDescription("desc");
        size.setPrice(1d);
        Set<TeaSize> sizes = new HashSet<>();
        sizes.add(size);
        return sizes;
    }
}
