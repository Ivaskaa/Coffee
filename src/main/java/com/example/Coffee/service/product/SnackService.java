package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.repository.product.SnackRepository;
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
public class SnackService {
    private final SnackRepository snackRepository;
    private final StaticService service;

    public List<Snack> findAllActive(){
        log.info("get all snacks");
        List<Snack> snacks = snackRepository.findAllActive();
        log.info("success");
        return snacks;
    }

    public Page<Snack> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get snack page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Snack> snacks = snackRepository.findAll(pageable);
        log.info("success");
        return snacks;
    }

    public Snack save(Snack snack, MultipartFile file) throws IOException {
        log.info("add new snack. snack: {}, file: {}", snack, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            snack.setPhoto(fileName);
            service.savePhoto("snack", file, fileName);
        }
        snackRepository.save(snack);
        log.info("success");
        return snack;
    }

    public Snack update(Long id, Snack snackForm, MultipartFile file) throws IOException {
        log.info("update snack: {}, file: {}", snackForm, file);
        Snack snack = snackRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("snack", snack.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            snack.setPhoto(fileName);
            service.savePhoto("snack", file, fileName);
        }
        snack.setName(snackForm.getName());
        snack.setDescription(snackForm.getDescription());
        snack.setActive(snackForm.isActive());
        if(snack.getSizes() != null) {
            if (!snack.getSizes().isEmpty()) {
                snack.getSizes().clear();
            }
            snack.getSizes().addAll(snackForm.getSizes());
        }
        snackRepository.save(snack);
        log.info("success");
        return snack;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete snack by id: {}", id);
        Snack snack = snackRepository.findById(id).orElseThrow();
        service.deletePhoto("snack", snack.getPhoto());
        snackRepository.deleteById(id);
        log.info("success");
    }

    public Snack findById(Long id) {
        log.info("get snack by id: {}", id);
        Snack snack = snackRepository.findById(id).orElseThrow();
        log.info("success");
        return snack;
    }
}
