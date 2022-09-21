package com.example.Coffee.repository;

import com.example.Coffee.entities.SliderImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderImageRepository extends JpaRepository<SliderImage, Long> {
}
