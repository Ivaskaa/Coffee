package com.example.Coffee.repository.product;

import com.example.Coffee.entities.product.coffee.Coffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Page<Coffee> findAll(Pageable pageable);
    @Query("select c from Coffee c where c.active = true")
    List<Coffee> findAllActive();

    @Query("SELECT count(c) FROM Coffee c WHERE c.active = true")
    Integer findCount();

    List<Coffee> findAllByActiveTrue();

    List<Coffee> findAllByActiveTrueOrId(Long id);
}
