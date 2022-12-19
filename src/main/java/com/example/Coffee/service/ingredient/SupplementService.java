package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.repository.ingredients.SupplementRepository;
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
public class SupplementService {
    private final SupplementRepository supplementRepository;

    public List<Supplement> findAllActive(Long id) {
        log.info("get all active supplements");
        List<Supplement> objects;
        if(id == null){
            objects = supplementRepository.findAllByActiveTrue();
        } else {
            objects = supplementRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Page<Supplement> findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        log.info("get supplement page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Supplement> supplements = supplementRepository.findAll(pageable);
        log.info("success");
        return supplements;
    }

    public Supplement save(Supplement supplement) {
        log.info("add new supplement: {}", supplement);
        supplementRepository.save(supplement);
        log.info("success");
        return supplement;
    }

    public Supplement update(Long id, Supplement supplementForm) {
        log.info("update supplement: {}", supplementForm);
        Supplement supplement = supplementRepository.findById(id).orElseThrow();
        supplement.setName(supplementForm.getName());
        supplement.setPrice(supplementForm.getPrice());
        supplement.setActive(supplementForm.isActive());
        supplementRepository.save(supplement);
        log.info("success");
        return supplement;
    }

    public void deleteById(Long id) {
        log.info("delete supplement by id: {}", id);
        supplementRepository.deleteById(id);
        log.info("success");
    }

    public Supplement findById(Long id) {
        log.info("get supplement by id: {}", id);
        Supplement supplement = supplementRepository.findById(id).orElseThrow();
        log.info("success");
        return supplement;
    }
}
