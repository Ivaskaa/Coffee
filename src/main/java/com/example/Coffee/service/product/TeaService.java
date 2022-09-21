package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.tea.Tea;
import com.example.Coffee.repository.product.TeaRepository;
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
public class TeaService {
    private final TeaRepository teaRepository;
    private final StaticService service;

    public List<Tea> findAllActive(){
        log.info("get all teas");
        List<Tea> teas = teaRepository.findAllActive();
        log.info("success");
        return teas;
    }

    public Page<Tea> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get tea page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Tea> coffees = teaRepository.findAll(pageable);
        log.info("success");
        return coffees;
    }

    public Tea save(Tea tea, MultipartFile file) throws IOException {
        log.info("add new tea. tea: {}, file: {}", tea, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            tea.setPhoto(fileName);
            service.savePhoto("tea", file, fileName);
        }
        teaRepository.save(tea);
        log.info("success");
        return tea;
    }

    public Tea update(Long id, Tea teaForm, MultipartFile file) throws IOException {
        log.info("update tea: {}, file: {}", teaForm, file);
        Tea tea = teaRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("tea", tea.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            tea.setPhoto(fileName);
            service.savePhoto("tea", file, fileName);
        }
        tea.setName(teaForm.getName());
        tea.setDescription(teaForm.getDescription());
        tea.setActive(teaForm.isActive());
        if(tea.getSizes() != null) {
            if (!tea.getSizes().isEmpty()) {
                tea.getSizes().clear();
            }
            tea.getSizes().addAll(teaForm.getSizes());
        }
        teaRepository.save(tea);
        log.info("success");
        return tea;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete tea by id: {}", id);
        Tea tea = teaRepository.findById(id).orElseThrow();
        service.deletePhoto("tea", tea.getPhoto());
        teaRepository.deleteById(id);
        log.info("success");
    }

    public Tea findById(Long id) {
        log.info("get tea by id: {}", id);
        Tea tea = teaRepository.findById(id).orElseThrow();
        log.info("success");
        return tea;
    }
}
