package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.repository.ingredients.SauceRepository;
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
public class SauceService {
    private final SauceRepository sauceRepository;

    public List<Sauce> findAllActive(Long id) {
        log.info("get all active sauces");
        List<Sauce> objects;
        if(id == null){
            objects = sauceRepository.findAllByActiveTrue();
        } else {
            objects = sauceRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Page<Sauce> findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        log.info("get sauce page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Sauce> sauces = sauceRepository.findAll(pageable);
        log.info("success");
        return sauces;
    }

    public Sauce save(Sauce sauce) {
        log.info("add new sauce: {}", sauce);
        sauceRepository.save(sauce);
        log.info("success");
        return sauce;
    }

    public Sauce update(Long id, Sauce sauceForm) {
        log.info("update sauce: {}", sauceForm);
        Sauce sauce = sauceRepository.findById(id).orElseThrow();
        sauce.setName(sauceForm.getName());
        sauce.setPrice(sauceForm.getPrice());
        sauce.setActive(sauceForm.isActive());
        sauceRepository.save(sauce);
        log.info("success");
        return sauce;
    }

    public void deleteById(Long id) {
        log.info("delete sauce by id: {}", id);
        sauceRepository.deleteById(id);
        log.info("success");
    }

    public Sauce findById(Long id) {
        log.info("get sauce by id: {}", id);
        Sauce sauce = sauceRepository.findById(id).orElseThrow();
        log.info("success");
        return sauce;
    }
}
