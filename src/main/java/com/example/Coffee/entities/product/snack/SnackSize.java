package com.example.Coffee.entities.product.snack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "size_snacks")
public class SnackSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 255, message = "Name should be less than 255 characters")
    private String name;
    @Size(max = 255, message = "Description should be less than 255 characters")
    private String description;
    private Double price;

    @JoinColumn(name = "snack_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonIgnore
    private Snack snack;

    @Override
    public String toString() {
        return "SnackSize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
