package com.example.Coffee.entities.product.coffee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "size_coffees")
public class CoffeeSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 255, message = "Name should be less than 255 characters")
    private String name;
    @Size(max = 255, message = "Description should be less than 255 characters")
    private String description;
    private Double price;

    @JoinColumn(name = "coffee_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonIgnore
    private Coffee coffee;

    @Override
    public String toString() {
        return "CoffeeSize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
