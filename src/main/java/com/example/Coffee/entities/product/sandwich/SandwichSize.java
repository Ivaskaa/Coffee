package com.example.Coffee.entities.product.sandwich;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "size_Sandwiches")
public class SandwichSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 255, message = "Name should be less than 255 characters")
    private String name;
    @Size(max = 255, message = "Description should be less than 255 characters")
    private String description;
    private Double price;

    @JoinColumn(name = "sandwich_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonIgnore
    private Sandwich sandwich;

    @Override
    public String toString() {
        return "SandwichSize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
