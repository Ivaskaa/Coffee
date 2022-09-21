package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.repository.product.CoffeeRepository;
import com.example.Coffee.service.StaticService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@Log4j2
@AllArgsConstructor
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    private final StaticService service;

    public List<Coffee> findAllActive(){
        log.info("get all coffees");
        List<Coffee> coffees = coffeeRepository.findAllActive();
        log.info("success");
        return coffees;
    }

    public Page<Coffee> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get coffee page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Coffee> coffees = coffeeRepository.findAll(pageable);
        log.info("success");
        return coffees;
    }

    public Coffee save(Coffee coffee, MultipartFile file) throws IOException {
        log.info("add new coffee. coffee: {}, file: {}", coffee, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            coffee.setPhoto(fileName);
            service.savePhoto("coffee", file, fileName);
        }
        coffeeRepository.save(coffee);
        log.info("success");
        return coffee;
    }

    public Coffee update(Long id, Coffee coffeeForm, MultipartFile file) throws IOException {
        log.info("update coffee: {}, file: {}", coffeeForm, file);
        Coffee coffee = coffeeRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("coffee", coffee.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            coffee.setPhoto(fileName);
            service.savePhoto("coffee", file, fileName);
        }
        coffee.setName(coffeeForm.getName());
        coffee.setDescription(coffeeForm.getDescription());
        coffee.setActive(coffeeForm.isActive());
        if(coffee.getSizes() != null) {
            if (!coffee.getSizes().isEmpty()) {
                coffee.getSizes().clear();
            }
            coffee.getSizes().addAll(coffeeForm.getSizes());
        }
        coffeeRepository.save(coffee);
        log.info("success");
        return coffee;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        Coffee coffee = coffeeRepository.findById(id).orElseThrow();
        service.deletePhoto("coffee", coffee.getPhoto());
        coffeeRepository.deleteById(id);
        log.info("delete coffee by id: {}", id);
    }

    public Coffee findById(Long id) {
        Coffee coffee = coffeeRepository.findById(id).orElseThrow();
        log.info("get coffee by id: {}", id);
        return coffee;
    }
}
