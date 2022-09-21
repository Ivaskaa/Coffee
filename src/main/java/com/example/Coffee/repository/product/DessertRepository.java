package com.example.Coffee.repository.product;

import com.example.Coffee.entities.product.dessert.Dessert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DessertRepository extends JpaRepository<Dessert, Long> {
    Page<Dessert> findAll(Pageable pageable);
    @Query("select d from Dessert d where d.active = true")
    List<Dessert> findAllActive();

    @Query("SELECT count(d) FROM Dessert d WHERE d.active = true")
    Integer findCount();
}
