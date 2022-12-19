package com.example.Coffee.entities.product.sandwich;

import com.example.Coffee.entities.order.sandwich.SandwichOrder;
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
@Table(name = "sandwiches")
public class Sandwich {
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

    @JoinColumn(name = "sandwich_id")
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = {CascadeType.ALL}
    )
    private List<SandwichSize> sizes;

    @JoinColumn(name = "sandwich_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<SandwichOrder> sandwichOrders;

    @Formula("(select (select AVG(cs.price)\n" +
            "        from size_sandwiches cs\n" +
            "        where cs.sandwich_id = c.id\n" +
            "        group by cs.sandwich_id)\n" +
            "from sandwiches c\n" +
            "where c.id = id)")
    private Double averagePrice;

    @Override
    public String toString() {
        return "Sandwich{" +
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
        Sandwich sandwich = (Sandwich) o;
        return active == sandwich.active && Objects.equals(id, sandwich.id) && Objects.equals(name, sandwich.name) && Objects.equals(photo, sandwich.photo) && Objects.equals(description, sandwich.description) && Objects.equals(sizes, sandwich.sizes) && Objects.equals(sandwichOrders, sandwich.sandwichOrders);
    }
}
