package com.example.Coffee.entities.product.coffee;

import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "coffees")
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title shouldn't be empty")
    @Size(max = 255, message = "Title should be less than 255 characters")
    private String name;
    private String photo;
    @NotBlank(message = "Description shouldn't be empty")
    @Size(max = 5000, message = "Description should be less than 255 characters")
    private String description;
    private boolean active;

    @JoinColumn(name = "coffee_id")
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = {CascadeType.ALL}
    )
    private List<CoffeeSize> sizes;

    @JoinColumn(name = "coffee_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<CoffeeOrder> coffeeOrders;

    @Formula("(select (select AVG(cs.price)\n" +
            "        from size_coffees cs\n" +
            "        where cs.coffee_id = c.id\n" +
            "        group by cs.coffee_id)\n" +
            "from coffees c\n" +
            "where c.id = id)")
    private Double averagePrice;

    @Override
    public String toString() {
        return "Coffee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", sizes=" + sizes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coffee coffee = (Coffee) o;
        return active == coffee.active && Objects.equals(id, coffee.id) && Objects.equals(name, coffee.name) && Objects.equals(photo, coffee.photo) && Objects.equals(description, coffee.description) && Objects.equals(sizes, coffee.sizes) && Objects.equals(coffeeOrders, coffee.coffeeOrders);
    }
}
