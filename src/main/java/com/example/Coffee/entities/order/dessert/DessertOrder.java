package com.example.Coffee.entities.order.dessert;

import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.entities.product.dessert.DessertSize;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "dessert_orders")
public class DessertOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "dessert_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Dessert dessert;

    @JoinColumn(name = "syrup_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Syrup syrup;

    @JoinColumn(name = "supplement_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Supplement supplement;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private DessertSize size;

    private Integer count;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonBackReference
    private Order order;

    private boolean active;

    @Formula("(select ((select sc.price from size_desserts sc where sc.id = co.size_id) +\n" +
            "       Coalesce((select s.price from supplements s where s.id = co.supplement_id), 0) +\n" +
            "       Coalesce((select s.price from syrups s where s.id = co.syrup_id), 0)) * co.count\n" +
            "    from dessert_orders co where co.id = id)")
    private Double price;

    @Override
    public String toString() {
        return "DessertOrder{" +
                "id=" + id +
                ", dessert=" + dessert +
                ", syrup=" + syrup +
                ", supplement=" + supplement +
                ", size=" + size +
                ", count=" + count +
                '}';
    }
}
