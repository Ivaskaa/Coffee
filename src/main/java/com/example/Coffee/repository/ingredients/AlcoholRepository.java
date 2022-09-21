package com.example.Coffee.repository.ingredients;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlcoholRepository extends JpaRepository<Alcohol, Long> {
    Page<Alcohol> findAll(Pageable pageable);
    @Query("select a from Alcohol a where a.active = true")
    List<Alcohol> findAllActive();

    @Query("SELECT count(a) FROM Alcohol a WHERE a.active = true")
    Integer findCount();
}
