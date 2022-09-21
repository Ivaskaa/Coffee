package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.sandwich.SandwichDto;
import com.example.Coffee.entities.product.sandwich.SandwichSize;
import com.example.Coffee.repository.product.SandwichRepository;
import com.example.Coffee.service.StaticService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class SandwichService {
    private final SandwichRepository sandwichRepository;
    private final StaticService service;

    public List<Sandwich> findAllActive(){
        log.info("get all sandwiches");
        List<Sandwich> sandwiches = sandwichRepository.findAllActive();
        log.info("success");
        return sandwiches;
    }

    public Page<Sandwich> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get sandwich page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Sandwich> sandwiches = sandwichRepository.findAll(pageable);
        log.info("success");
        return sandwiches;
    }

    public Sandwich save(Sandwich sandwich, MultipartFile file) throws IOException {
        log.info("add new sandwich. sandwich: {}, file: {}", sandwich, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            sandwich.setPhoto(fileName);
            service.savePhoto("sandwich", file, fileName);
        }
        sandwichRepository.save(sandwich);
        log.info("success");
        return sandwich;
    }

    public Sandwich update(Long id, Sandwich sandwichForm, MultipartFile file) throws IOException {
        log.info("update sandwich: {}, file: {}", sandwichForm, file);
        Sandwich sandwich = sandwichRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("sandwich", sandwich.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            sandwich.setPhoto(fileName);
            service.savePhoto("sandwich", file, fileName);
        }
        sandwich.setName(sandwichForm.getName());
        sandwich.setDescription(sandwichForm.getDescription());
        sandwich.setActive(sandwichForm.isActive());
        if(sandwich.getSizes() != null) {
            if (!sandwich.getSizes().isEmpty()) {
                sandwich.getSizes().clear();
            }
            sandwich.getSizes().addAll(sandwichForm.getSizes());
        }
        sandwichRepository.save(sandwich);
        log.info("success");
        return sandwich;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete sandwich by id: {}", id);
        Sandwich sandwich = sandwichRepository.findById(id).orElseThrow();
        service.deletePhoto("sandwich", sandwich.getPhoto());
        sandwichRepository.deleteById(id);
        log.info("success");
    }

    public Sandwich findById(Long id) {
        log.info("get sandwich by id: {}", id);
        Sandwich sandwich = sandwichRepository.findById(id).orElseThrow();
        log.info("success");
        return sandwich;
    }

}
