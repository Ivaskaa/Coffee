package com.example.Coffee.entities.product.sandwich;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name = "size_Sandwiches")
public class SandwichSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotBlank(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String description;
    @NotNull(message = "Must not be empty")
    @Min(value = 1, message = "Must be greater then 0")
    private Double price;

    @Transient
    private Integer number;

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
