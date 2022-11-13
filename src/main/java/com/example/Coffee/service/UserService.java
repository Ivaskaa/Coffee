package com.example.Coffee.service;

import com.example.Coffee.entities.user.User;
import com.example.Coffee.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Page<User> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get user page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<User> users = userRepository.findAll(pageable);
        log.info("success");
        return users;
    }

    public User update(Long id, User userForm) {
        log.info("update user: {}", userForm);
        User user = userRepository.findById(id).orElseThrow();
        user.setPhone(userForm.getPhone());
        if(userForm.getPassword() != null && !userForm.getPassword().equals("")) {
            user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        }
        user.setName(userForm.getName());
        if(userForm.getBirthday() != null){
            if (!userForm.getBirthday().equals("")) {
                user.setBirthday(userForm.getBirthday());
            } else {
                user.setBirthday(null);
            }
        }
        user.setLocation(userForm.getLocation());
        user.setActive(userForm.isActive());
        userRepository.save(user);
        log.info("success");
        return user;
    }

    public void deleteById(Long id) {
        log.info("delete user by id: {}", id);
        userRepository.deleteById(id);
        log.info("success");
    }

    public User findById(Long id) {
        log.info("get user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow();
        log.info("success user: {}", user);
        return user;
    }

    public User loadUserByPhone(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone);
        if(user == null){
            throw new UsernameNotFoundException("User with phone " + phone + "not found");
        }
        return user;
    }

    public boolean checkPhone(String phone) {
        return phone.contains("_");
    }

    public boolean checkUnicPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        return user != null;
    }
}
