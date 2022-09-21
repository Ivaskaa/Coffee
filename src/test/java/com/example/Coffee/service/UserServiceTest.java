package com.example.Coffee.service;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Education;
import com.example.Coffee.entities.user.User;
import com.example.Coffee.repository.EducationRepository;
import com.example.Coffee.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void findSortingPage() {
        userService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(userRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void update() {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        user.setPhone("phone");
        user.setActive(true);

        Mockito.doReturn(Optional.of(new User()))
                .when(userRepository)
                .findById(1L);

        Mockito.doReturn("password")
                .when(passwordEncoder)
                .encode(user.getPassword());

        User checkedUser = userService.update(1L, user);
        Assertions.assertEquals(user, checkedUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("password");
    }

    @Test
    void deleteById() {
        userService.deleteById(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {

        Mockito.doReturn(Optional.of(new User()))
                .when(userRepository)
                .findById(1L);

        userService.findById(1L);
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }
}