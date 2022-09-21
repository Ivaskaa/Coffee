package com.example.Coffee.entities.product.dessert;

import com.example.Coffee.entities.order.dessert.DessertOrder;
import com.example.Coffee.entities.product.coffee.Coffee;
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
@Table(name = "desserts")
public class Dessert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Title shouldn't be empty")
    @Size(max = 255, message = "Title should be less than 255 characters")
    private String name;
    private String photo;
    @NotEmpty(message = "Description shouldn't be empty")
    @Size(max = 255, message = "Description should be less than 255 characters")
    private String description;
    private boolean active;

    @JoinColumn(name = "dessert_id")
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = {CascadeType.ALL}
    )
    private Set<DessertSize> sizes;

    @JoinColumn(name = "dessert_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<DessertOrder> dessertOrders;

    @Override
    public String toString() {
        return "Dessert{" +
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
        Dessert dessert = (Dessert) o;
        return active == dessert.active && Objects.equals(id, dessert.id) && Objects.equals(name, dessert.name) && Objects.equals(photo, dessert.photo) && Objects.equals(description, dessert.description) && Objects.equals(sizes, dessert.sizes) && Objects.equals(dessertOrders, dessert.dessertOrders);
    }
}
