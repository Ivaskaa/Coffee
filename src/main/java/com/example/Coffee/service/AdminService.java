package com.example.Coffee.service;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Role;
import com.example.Coffee.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collections;

@Service
@Log4j2
public class AdminService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Page<Admin> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Admin> admins = adminRepository.findAll(pageable);
        log.info("get admin page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        return admins;
    }

    public Admin saveAdmin(Admin admin) {
        log.info("add new admin. admin: {}", admin);
        admin.setAdminRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.todayRegistrationDate();
        adminRepository.save(admin);
        log.info("success");
        return admin;
    }

    public Admin updateAdmin(Long id, Admin adminForm) {
        log.info("update admin. id: {}, admin: {}", id, adminForm);
        Admin admin = adminRepository.findById(id).orElseThrow();
        admin.setUsername(adminForm.getUsername());
        admin.setPassword(passwordEncoder.encode(adminForm.getPassword()));
        admin.setActive(adminForm.isActive());
        adminRepository.save(admin);
        log.info("success");
        return admin;
    }

    public void deleteById(Long id) {
        log.info("delete admin by id: {}", id);
        adminRepository.deleteById(id);
        log.info("success");
    }

    public Admin findById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow();
        log.info("get admin by id: {}", id);
        return admin;
    }

    @Override
    public Admin loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("User with username " + username + "not found");
        }
        log.info("username: {} want to login", username);
        return admin;
    }
}
