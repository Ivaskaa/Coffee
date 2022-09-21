package com.example.Coffee.entities.ingredients.sauce;

import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.entities.order.snack.SnackOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sauces")
public class Sauce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(max = 255, message = "Name should be less than 255 characters")
    private String name;
    private Double price;
    private boolean active;

    @JoinColumn(name = "sauce_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<SandwichOrder> sandwichOrders;

    @JoinColumn(name = "sauce_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<SnackOrder> snackOrders;

    @Override
    public String toString() {
        return "Sauce{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sauce sauce = (Sauce) o;
        return active == sauce.active && Objects.equals(id, sauce.id) && Objects.equals(name, sauce.name) && Objects.equals(price, sauce.price) && Objects.equals(sandwichOrders, sauce.sandwichOrders) && Objects.equals(snackOrders, sauce.snackOrders);
    }
}
