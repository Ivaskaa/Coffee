package com.example.Coffee.entities.ingredients.syrup;

import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.order.dessert.DessertOrder;
import com.example.Coffee.entities.order.tea.TeaOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "syrups")
public class Syrup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotNull(message = "Must not be empty")
    @DecimalMin(value = "0.01", message = "Must be greater then 0.01")
    @DecimalMax(value = "1000.0", message = "Must be less then 1000")
    private BigDecimal price;
    private boolean active;

    @JoinColumn(name = "syrup_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<CoffeeOrder> coffeeOrders;

    @JoinColumn(name = "syrup_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<TeaOrder> teaOrders;

    @JoinColumn(name = "syrup_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<DessertOrder> dessertOrders;

    @Override
    public String toString() {
        return "Syrup{" +
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
        Syrup syrup = (Syrup) o;
        return active == syrup.active && Objects.equals(id, syrup.id) && Objects.equals(name, syrup.name) && Objects.equals(price, syrup.price) && Objects.equals(coffeeOrders, syrup.coffeeOrders) && Objects.equals(teaOrders, syrup.teaOrders) && Objects.equals(dessertOrders, syrup.dessertOrders);
    }
}
