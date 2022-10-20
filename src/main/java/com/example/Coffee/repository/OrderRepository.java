package com.example.Coffee.repository;

import com.example.Coffee.entities.order.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Page<Order> findAll(Specification<Order> specification, Pageable pageable);
//    List<Order> findAll();
//
//    @Query("SELECT count(o) FROM Order o")
//    Integer findCount();

//    Page<Order> findAll(Pageable pageable);
//    @Query("select o from Order o where o.user.name like %?2% and o.id = ?1")
//    Page<Order> findByUserAndId(Long id, String name, Pageable pageable);
//    @Query("select o from Order o where o.user.name like %?1%")
//    Page<Order> findByUser(String name, Pageable pageable);
}
