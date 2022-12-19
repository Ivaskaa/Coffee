package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.repository.ingredients.MilkRepository;
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
public class MilkService {
    private final MilkRepository milkRepository;

    public List<Milk> findAllActive(Long id) {
        log.info("get all active milks");
        List<Milk> objects;
        if(id == null){
            objects = milkRepository.findAllByActiveTrue();
        } else {
            objects = milkRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Page<Milk> findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        log.info("get milk page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Milk> milks = milkRepository.findAll(pageable);

        return milks;
    }

    public Milk save(Milk milk) {
        log.info("add new milk: {}", milk);
        milkRepository.save(milk);
        log.info("success");
        return milk;
    }

    public Milk update(Long id, Milk milkForm) {
        log.info("update milk: {}", milkForm);
        Milk milk = milkRepository.findById(id).orElseThrow();
        milk.setName(milkForm.getName());
        milk.setPrice(milkForm.getPrice());
        milk.setActive(milkForm.isActive());
        milkRepository.save(milk);
        log.info("success");
        return milk;
    }

    public void deleteById(Long id) {
        log.info("delete milk by id: {}", id);
        milkRepository.deleteById(id);
        log.info("success");
    }

    public Milk findById(Long id) {
        log.info("get milk by id: {}", id);
        Milk milk = milkRepository.findById(id).orElseThrow();
        log.info("success");
        return milk;
    }


}
