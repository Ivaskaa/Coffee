package com.example.Coffee.repository;

import com.example.Coffee.entities.Education;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    Page<Education> findAll(Pageable pageable);
}
