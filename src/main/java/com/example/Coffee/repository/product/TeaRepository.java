package com.example.Coffee.repository.product;

import com.example.Coffee.entities.product.tea.Tea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeaRepository extends JpaRepository<Tea, Long> {
    Page<Tea> findAll(Pageable pageable);
    @Query("select t from Tea t where t.active = true")
    List<Tea> findAllActive();

    @Query("SELECT count(t) FROM Tea t WHERE t.active = true")
    Integer findCount();
}
