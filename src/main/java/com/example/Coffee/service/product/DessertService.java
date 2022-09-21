package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.repository.product.DessertRepository;
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
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class DessertService {
    private final DessertRepository dessertRepository;
    private final StaticService service;

    public List<Dessert> findAllActive(){
        log.info("get all desserts");
        List<Dessert> desserts = dessertRepository.findAllActive();
        log.info("success");
        return desserts;
    }

    public Page<Dessert> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get dessert page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Dessert> coffees = dessertRepository.findAll(pageable);
        log.info("success");
        return coffees;
    }

    public Dessert save(Dessert dessert, MultipartFile file) throws IOException {
        log.info("add new dessert. dessert: {}, file: {}", dessert, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            dessert.setPhoto(fileName);
            service.savePhoto("dessert", file, fileName);
        }
        dessertRepository.save(dessert);
        log.info("success");
        return dessert;
    }

    public Dessert update(Long id, Dessert dessertForm, MultipartFile file) throws IOException {
        log.info("update dessert: {}, file: {}", dessertForm, file);
        Dessert dessert = dessertRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("dessert", dessert.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            dessert.setPhoto(fileName);
            service.savePhoto("dessert", file, fileName);
        }
        dessert.setName(dessertForm.getName());
        dessert.setDescription(dessertForm.getDescription());
        dessert.setActive(dessertForm.isActive());
        if(dessert.getSizes() != null) {
            if (!dessert.getSizes().isEmpty()) {
                dessert.getSizes().clear();
            }
            dessert.getSizes().addAll(dessertForm.getSizes());
        }
        dessertRepository.save(dessert);
        log.info("success");
        return dessert;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete dessert by id: {}", id);
        Dessert dessert = dessertRepository.findById(id).orElseThrow();
        service.deletePhoto("dessert", dessert.getPhoto());
        dessertRepository.deleteById(id);
        log.info("success");
    }

    public Dessert findById(Long id) {
        log.info("get dessert by id: {}", id);
        Dessert dessert = dessertRepository.findById(id).orElseThrow();
        log.info("success");
        return dessert;
    }
}
