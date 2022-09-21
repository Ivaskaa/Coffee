package com.example.Coffee.repository;

import com.example.Coffee.entities.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findAll(Pageable pageable);

    @Query("SELECT count(f) FROM Feedback f")
    Integer findCount();
}
