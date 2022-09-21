package com.example.Coffee.entities.user;

import com.example.Coffee.entities.Location;
import com.example.Coffee.entities.Role;
import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.order.order.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private String name;
    private String password;
    @JoinColumn(name = "location_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    private Location location;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private Set<Role> roles;

    @JoinColumn(name = "user_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<Order> orders;
    private String registrationDate;
    private String birthday;

    private Integer points;
    private boolean active;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                ", roles=" + roles +
                ", registrationDate=" + registrationDate +
                ", birthday=" + birthday +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return active == user.active && Objects.equals(id, user.id) && Objects.equals(phone, user.phone) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(location, user.location) && Objects.equals(roles, user.roles) && Objects.equals(orders, user.orders) && Objects.equals(registrationDate, user.registrationDate) && Objects.equals(birthday, user.birthday) && Objects.equals(points, user.points);
    }
}
