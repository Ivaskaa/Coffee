package com.example.Coffee.entities.order.snack;

import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.entities.product.snack.SnackSize;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "snack_orders")
public class SnackOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "snack_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Snack snack;

    @JoinColumn(name = "sauce_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Sauce sauce;

    @JoinColumn(name = "supplement_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Supplement supplement;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private SnackSize size;

    private Integer count;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonBackReference
    private Order order;

    private boolean active;

    @Override
    public String toString() {
        return "SnackOrder{" +
                "id=" + id +
                ", snack=" + snack +
                ", sauce=" + sauce +
                ", supplement=" + supplement +
                ", size=" + size +
                ", count=" + count +
                '}';
    }
}
