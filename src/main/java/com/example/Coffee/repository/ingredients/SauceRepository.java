package com.example.Coffee.repository.ingredients;

import com.example.Coffee.entities.ingredients.sauce.Sauce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SauceRepository extends JpaRepository<Sauce, Long> {
    Page<Sauce> findAll(Pageable pageable);
    @Query("select s from Sauce s where s.active = true")
    List<Sauce> findAllActive();

    @Query("SELECT count(s) FROM Sauce s WHERE s.active = true")
    Integer findCount();

    List<Sauce> findAllByActiveTrue();

    List<Sauce> findAllByActiveTrueOrId(Long id);
}