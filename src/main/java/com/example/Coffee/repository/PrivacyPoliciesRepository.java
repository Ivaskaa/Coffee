package com.example.Coffee.repository;

import com.example.Coffee.entities.PrivacyPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivacyPoliciesRepository extends JpaRepository<PrivacyPolicy, Long> {
}
