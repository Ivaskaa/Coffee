package com.example.Coffee.repository;


import com.example.Coffee.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhone(String phone);
    Page<User> findAll(Pageable pageable);

    @Query("SELECT count(u) FROM User u WHERE u.active = true")
    Integer findCount();
}
