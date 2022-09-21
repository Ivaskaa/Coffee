package com.example.Coffee.repository.orderDetails;

import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
    @Query("select co from CoffeeOrder co where co.order.id = :orderId")
    Page<CoffeeOrder> findAllByOrderId(@Param("orderId") Long orderId, Pageable pageable);

    @Query("SELECT count(c) FROM CoffeeOrder c WHERE c.active = true")
    Integer findCount();


//    SELECT * FROM coffee.coffee_orders co
//    JOIN orders_coffee_orders oco ON(co.id = oco.coffee_order_id)
//    JOIN orders o ON(oco.order_id = o.id) where o.id = 1
}
