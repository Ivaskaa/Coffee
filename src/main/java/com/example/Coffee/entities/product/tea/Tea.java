package com.example.Coffee.entities.product.tea;

import com.example.Coffee.entities.order.tea.TeaOrder;
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
@Table(name = "teas")
public class Tea {
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

    @JoinColumn(name = "tea_id")
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = {CascadeType.ALL}
    )
    private List<TeaSize> sizes;

    @JoinColumn(name = "tea_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<TeaOrder> teaOrders;

    @Formula("(select (select AVG(cs.price)\n" +
            "        from size_teas cs\n" +
            "        where cs.tea_id = c.id\n" +
            "        group by cs.tea_id)\n" +
            "from teas c\n" +
            "where c.id = id)")
    private Double averagePrice;

    @Override
    public String toString() {
        return "Tea{" +
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
        Tea tea = (Tea) o;
        return active == tea.active && Objects.equals(id, tea.id) && Objects.equals(name, tea.name) && Objects.equals(photo, tea.photo) && Objects.equals(description, tea.description) && Objects.equals(sizes, tea.sizes) && Objects.equals(teaOrders, tea.teaOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photo, description, active, sizes, teaOrders);
    }
}
