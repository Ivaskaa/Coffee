package com.example.Coffee.entities.product.snack;

import com.example.Coffee.entities.order.snack.SnackOrder;
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
@Table(name = "snacks")
public class Snack {
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

    @JoinColumn(name = "snack_id")
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = {CascadeType.ALL}
    )
    private List<SnackSize> sizes;

    @JoinColumn(name = "snack_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<SnackOrder> snackOrders;

    @Formula("(select (select AVG(cs.price)\n" +
            "        from size_snacks cs\n" +
            "        where cs.snack_id = c.id\n" +
            "        group by cs.snack_id)\n" +
            "from snacks c\n" +
            "where c.id = id)")
    private Double averagePrice;

    @Override
    public String toString() {
        return "Snack{" +
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
        Snack snack = (Snack) o;
        return active == snack.active && Objects.equals(id, snack.id) && Objects.equals(name, snack.name) && Objects.equals(photo, snack.photo) && Objects.equals(description, snack.description) && Objects.equals(sizes, snack.sizes) && Objects.equals(snackOrders, snack.snackOrders);
    }
}
