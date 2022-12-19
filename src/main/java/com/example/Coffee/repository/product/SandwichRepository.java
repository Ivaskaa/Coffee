package com.example.Coffee.repository.product;

import com.example.Coffee.entities.product.sandwich.Sandwich;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SandwichRepository extends JpaRepository<Sandwich, Long> {
    Page<Sandwich> findAll(Pageable pageable);
    @Query("select s from Sandwich s where s.active = true")
    List<Sandwich> findAllActive();

    @Query("SELECT count(s) FROM Sandwich s WHERE s.active = true")
    Integer findCount();

    List<Sandwich> findAllByActiveTrue();

    List<Sandwich> findAllByActiveTrueOrId(Long id);
}
