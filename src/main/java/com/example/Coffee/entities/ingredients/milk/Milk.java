package com.example.Coffee.entities.ingredients.milk;

import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.order.tea.TeaOrder;
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
@Table(name = "milks")
public class Milk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(max = 255, message = "Name should be less than 255 characters")
    private String name;
    private Double price;
    private boolean active;

    @JoinColumn(name = "milk_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<CoffeeOrder> coffeeOrders;

    @JoinColumn(name = "milk_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<TeaOrder> teaOrders;

    @Override
    public String toString() {
        return "Milk{" +
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
        Milk milk = (Milk) o;
        return active == milk.active && Objects.equals(id, milk.id) && Objects.equals(name, milk.name) && Objects.equals(price, milk.price) && Objects.equals(coffeeOrders, milk.coffeeOrders) && Objects.equals(teaOrders, milk.teaOrders);
    }
}
