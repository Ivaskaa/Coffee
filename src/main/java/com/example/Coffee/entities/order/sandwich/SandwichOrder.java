package com.example.Coffee.entities.order.sandwich;

import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.sandwich.SandwichSize;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sandwich_orders")
public class SandwichOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sandwich_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Sandwich sandwich;

    @JoinColumn(name = "sauce_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Sauce sauce;

    @JoinColumn(name = "supplement_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Supplement supplement;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private SandwichSize size;

    private Integer count;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonBackReference
    private Order order;

    private boolean active;

    @Formula("(select ((select sc.price from size_sandwiches sc where sc.id = co.size_id) +\n" +
            "        Coalesce((select s.price from supplements s where s.id = co.supplement_id), 0) +\n" +
            "        Coalesce((select s.price from sauces s where s.id = co.sauce_id), 0)) * co.count\n" +
            "    from sandwich_orders co where co.id = id)")
    private Double price;

    @Override
    public String toString() {
        return "SandwichOrder{" +
                "id=" + id +
                ", sandwich=" + sandwich +
                ", sauce=" + sauce +
                ", supplement=" + supplement +
                ", size=" + size +
                ", count=" + count +
                '}';
    }
}
