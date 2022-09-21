package com.example.Coffee.repository.orderDetails;

import com.example.Coffee.entities.order.dessert.DessertOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DessertOrderRepository extends JpaRepository<DessertOrder, Long> {
    @Query("select do from DessertOrder do where do.order.id = :orderId")
    Page<DessertOrder> findAllByOrderId(@Param("orderId") Long orderId, Pageable pageable);

    @Query("SELECT count(d) FROM DessertOrder d WHERE d.active = true")
    Integer findCount();
}