package com.example.Coffee.entities.order.order;


import com.example.Coffee.entities.PaymentForm;
import com.example.Coffee.entities.city.City;
import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.user.User;
import com.example.Coffee.entities.order.dessert.DessertOrder;
import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.entities.order.snack.SnackOrder;
import com.example.Coffee.entities.order.tea.TeaOrder;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private User user;
    private String date;
    private String time;
    @JoinColumn(name = "order_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<CoffeeOrder> coffeeOrder;
    @JoinColumn(name = "order_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<TeaOrder> teaOrder;
    @JoinColumn(name = "order_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<DessertOrder> dessertOrder;
    @JoinColumn(name = "order_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<SandwichOrder> sandwichOrder;
    @JoinColumn(name = "order_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<SnackOrder> snackOrder;
    @JoinColumn(name = "city_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private City city;
    private String home;
    private String entrance;
    private String flat;
    @JoinColumn(name = "paymentForm_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private PaymentForm paymentForm;
    private String prepareCash;
    private String comment;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", coffeeOrder=" + coffeeOrder +
                ", teaOrder=" + teaOrder +
                ", dessertOrder=" + dessertOrder +
                ", sandwichOrder=" + sandwichOrder +
                ", snackOrder=" + snackOrder +
                ", city=" + city +
                ", home='" + home + '\'' +
                ", entrance='" + entrance + '\'' +
                ", flat='" + flat + '\'' +
                ", paymentForm=" + paymentForm +
                ", prepareCash='" + prepareCash + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
