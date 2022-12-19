package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.repository.ingredients.SyrupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class SyrupService {
    private final SyrupRepository syrupRepository;

    public List<Syrup> findAllActive(Long id) {
        log.info("get all active syrups");
        List<Syrup> objects;
        if(id == null){
            objects = syrupRepository.findAllByActiveTrue();
        } else {
            objects = syrupRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Page<Syrup> findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        log.info("get syrup page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Syrup> syrups = syrupRepository.findAll(pageable);
        log.info("success");
        return syrups;
    }

    public Syrup save(Syrup syrup) {
        log.info("add new syrup: {}", syrup);
        syrupRepository.save(syrup);
        log.info("success");
        return syrup;
    }

    public Syrup update(Long id, Syrup syrupForm) {
        log.info("update syrup: {}", syrupForm);
        Syrup syrup = syrupRepository.findById(id).orElseThrow();
        syrup.setName(syrupForm.getName());
        syrup.setPrice(syrupForm.getPrice());
        syrup.setActive(syrupForm.isActive());
        syrupRepository.save(syrup);
        log.info("success");
        return syrup;
    }

    public void deleteById(Long id) {
        log.info("delete syrup by id: {}", id);
        syrupRepository.deleteById(id);
        log.info("success");
    }

    public Syrup findById(Long id) {
        log.info("get syrup by id: {}", id);
        Syrup syrup = syrupRepository.findById(id).orElseThrow();
        log.info("success");
        return syrup;
    }

}
