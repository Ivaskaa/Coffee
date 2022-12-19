package com.example.Coffee.others;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.entities.product.coffee.Coffee;

import java.math.BigDecimal;

public class IngredientsFactory {
    public Alcohol getAlcohol(){
        Alcohol ingredient = new Alcohol();
        ingredient.setName("name");
        ingredient.setPrice(new BigDecimal("1"));
        ingredient.setActive(true);
        return ingredient;
    }
    public Milk getMilk(){
        Milk ingredient = new Milk();
        ingredient.setName("name");
        ingredient.setPrice(new BigDecimal("1"));
        ingredient.setActive(true);
        return ingredient;
    }
    public Sauce getSauce(){
        Sauce ingredient = new Sauce();
        ingredient.setName("name");
        ingredient.setPrice(new BigDecimal("1"));
        ingredient.setActive(true);
        return ingredient;
    }
    public Supplement getSupplement(){
        Supplement ingredient = new Supplement();
        ingredient.setName("name");
        ingredient.setPrice(new BigDecimal("1"));
        ingredient.setActive(true);
        return ingredient;
    }
    public Syrup getSyrup(){
        Syrup ingredient = new Syrup();
        ingredient.setName("name");
        ingredient.setPrice(new BigDecimal("1"));
        ingredient.setActive(true);
        return ingredient;
    }
}
