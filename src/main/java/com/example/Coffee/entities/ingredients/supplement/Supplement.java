package com.example.Coffee.entities.ingredients.supplement;

import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.order.dessert.DessertOrder;
import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.entities.order.snack.SnackOrder;
import com.example.Coffee.entities.order.tea.TeaOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "supplements")
public class Supplement {
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

    @JoinColumn(name = "supplement_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<CoffeeOrder> coffeeOrders;

    @JoinColumn(name = "supplement_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<TeaOrder> teaOrders;

    @JoinColumn(name = "supplement_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<DessertOrder> dessertOrders;

    @JoinColumn(name = "supplement_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<SandwichOrder> sandwichOrders;

    @JoinColumn(name = "supplement_id")
    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<SnackOrder> snackOrders;

    @Override
    public String toString() {
        return "Supplement{" +
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
        Supplement that = (Supplement) o;
        return active == that.active && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(coffeeOrders, that.coffeeOrders) && Objects.equals(teaOrders, that.teaOrders) && Objects.equals(dessertOrders, that.dessertOrders) && Objects.equals(sandwichOrders, that.sandwichOrders) && Objects.equals(snackOrders, that.snackOrders);
    }
}
