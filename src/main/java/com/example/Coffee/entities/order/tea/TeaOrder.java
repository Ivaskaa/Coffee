package com.example.Coffee.entities.order.tea;

import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.product.tea.Tea;
import com.example.Coffee.entities.product.tea.TeaSize;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tea_orders")
public class TeaOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "tea_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Tea tea;

    @JoinColumn(name = "syrup_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Syrup syrup;

    @JoinColumn(name = "milk_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Milk milk;

    @JoinColumn(name = "supplement_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonManagedReference
    private Supplement supplement;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private TeaSize size;

    private Integer count;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JsonBackReference
    private Order order;

    private boolean active;

    @Formula("(select ((select sc.price from size_teas sc where sc.id = co.size_id) +\n" +
            "        Coalesce((select s.price from supplements s where s.id = co.supplement_id), 0) +\n" +
            "        Coalesce((select m.price from milks m where m.id = co.milk_id), 0) +\n" +
            "        Coalesce((select s.price from syrups s where s.id = co.syrup_id), 0)) * co.count\n" +
            "    from tea_orders co where co.id = 66)")
    private Double price;

    @Override
    public String toString() {
        return "TeaOrder{" +
                "id=" + id +
                ", tea=" + tea +
                ", syrup=" + syrup +
                ", milk=" + milk +
                ", supplement=" + supplement +
                ", size=" + size +
                ", count=" + count +
                '}';
    }
}
