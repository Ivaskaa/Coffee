package com.example.Coffee.entities.order.coffee;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "coffee_orders")
public class CoffeeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "coffee_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Coffee coffee;

    @JoinColumn(name = "syrup_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Syrup syrup;

    @JoinColumn(name = "alcohol_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Alcohol alcohol;

    @JoinColumn(name = "milk_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Milk milk;

    @JoinColumn(name = "supplement_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Supplement supplement;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private CoffeeSize size;

    private Integer count;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonBackReference
    private Order order;

    private boolean active;

    @Formula("(select ((select sc.price from size_coffees sc where sc.id = co.size_id) +\n" +
            "       Coalesce((select m.price from milks m where m.id = co.milk_id), 0) +\n" +
            "       Coalesce((select s.price from supplements s where s.id = co.supplement_id), 0) +\n" +
            "       Coalesce((select s.price from syrups s where s.id = co.syrup_id), 0) +\n" +
            "       Coalesce((select a.price from alcohols a where a.id = co.alcohol_id), 0)) * co.count\n" +
            "    from coffee_orders co where co.id = id)")
    private Double price;

    @Override
    public String toString() {
        return "CoffeeOrder{" +
                "id=" + id +
                ", coffee=" + coffee +
                ", syrup=" + syrup +
                ", alcohol=" + alcohol +
                ", milk=" + milk +
                ", supplement=" + supplement +
                ", size=" + size +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeOrder that = (CoffeeOrder) o;
        return active == that.active && Objects.equals(id, that.id) && Objects.equals(coffee, that.coffee) && Objects.equals(syrup, that.syrup) && Objects.equals(alcohol, that.alcohol) && Objects.equals(milk, that.milk) && Objects.equals(supplement, that.supplement) && Objects.equals(size, that.size) && Objects.equals(count, that.count);
    }
}
