package com.example.Coffee.entities.ingredients.alcohol;

import com.example.Coffee.entities.order.coffee.CoffeeOrder;
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
@Table(name = "alcohols")
public class Alcohol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(max = 255, message = "Name should be less than 255 characters")
    private String name;
    private Double price;
    private boolean active;

    @JoinColumn(name = "alcohol_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<CoffeeOrder> coffeeOrders;

    @Override
    public String toString() {
        return "Alcohol{" +
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
        Alcohol alcohol = (Alcohol) o;
        return active == alcohol.active && Objects.equals(id, alcohol.id) && Objects.equals(name, alcohol.name) && Objects.equals(price, alcohol.price) && Objects.equals(coffeeOrders, alcohol.coffeeOrders);
    }
}
