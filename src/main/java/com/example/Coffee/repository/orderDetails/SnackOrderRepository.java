package com.example.Coffee.repository.orderDetails;

import com.example.Coffee.entities.order.snack.SnackOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SnackOrderRepository extends JpaRepository<SnackOrder, Long> {
    @Query("select so from SnackOrder so where so.order.id = :orderId")
    Page<SnackOrder> findAllByOrderId(@Param("orderId") Long orderId, Pageable pageable);

    @Query("SELECT count(s) FROM SnackOrder s WHERE s.active = true")
    Integer findCount();
}
