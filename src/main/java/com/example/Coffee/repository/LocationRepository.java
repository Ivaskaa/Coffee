package com.example.Coffee.repository;
import com.example.Coffee.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findAll(Pageable pageable);

    @Query("select l from Location l where l.active = true")
    List<Location> findAllActive();

    @Query("SELECT count(l) FROM Location l WHERE l.active = true")
    Integer findCount();

    List<Location> findAllByActiveTrue();

    List<Location> findAllByActiveTrueOrId(Long id);
}
