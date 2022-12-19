package com.example.Coffee.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotNull(message = "Must be valid")
    @Min(value = 0, message = "Must be greater then 0")
    @Max(value = 360, message = "Must be less then 360")
    private Double latitude; // широта
    @NotNull(message = "Must be valid")
    @Min(value = 0, message = "Must be greater then 0")
    @Max(value = 360, message = "Must be less then 360")
    private Double longitude; // довгота
    private boolean active;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return active == location.active && Objects.equals(id, location.id) && Objects.equals(name, location.name) && Objects.equals(latitude, location.latitude) && Objects.equals(longitude, location.longitude);
    }
}
