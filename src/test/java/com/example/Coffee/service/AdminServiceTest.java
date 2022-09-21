package com.example.Coffee.service;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.repository.AdminRepository;
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


@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @MockBean
    private AdminRepository adminRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void findSortingPage() {
        adminService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(adminRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void saveAdmin() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.setActive(true);
        admin = adminService.saveAdmin(admin);
        Assertions.assertEquals("admin", admin.getUsername());
        Assertions.assertNotNull(admin.getRegistrationDate());
        Mockito.verify(adminRepository, Mockito.times(1)).save(admin);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("password");
    }

    @Test
    void updateAdmin() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.setActive(false);

        Mockito.doReturn(Optional.of(new Admin()))
                .when(adminRepository)
                .findById(1L);

        admin = adminService.updateAdmin(1L, admin);
        Assertions.assertEquals("admin", admin.getUsername());
        Assertions.assertFalse(admin.isActive());
        Mockito.verify(adminRepository, Mockito.times(1)).save(admin);
        Mockito.verify(adminRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("password");
    }

    @Test
    void deleteById() {
        adminService.deleteById(1L);
        Mockito.verify(adminRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Admin()))
                .when(adminRepository)
                .findById(1L);
        adminService.findById(1L);
        Mockito.verify(adminRepository, Mockito.times(1)).findById(1L);
    }
}