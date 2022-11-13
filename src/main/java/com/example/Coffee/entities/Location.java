package com.example.Coffee.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;
    @NotNull(message = "Must be valid")
    private Double latitude; // широта
    @NotNull(message = "Must be valid")
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
