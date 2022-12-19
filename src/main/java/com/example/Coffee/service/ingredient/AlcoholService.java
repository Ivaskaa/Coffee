package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.repository.ingredients.AlcoholRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class AlcoholService {
    private final AlcoholRepository alcoholRepository;

    public List<Alcohol> findAllActive(Long id){
        log.info("get all active alcohols");
        List<Alcohol> objects;
        if(id == null){
            objects = alcoholRepository.findAllByActiveTrue();
        } else {
            objects = alcoholRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Object findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        log.info("get alcohol page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Alcohol> alcoholPage = alcoholRepository.findAll(pageable);
        log.info("success");
        return alcoholPage;
    }

    public Alcohol save(Alcohol alcohol) {
        log.info("add new alcohol: {}", alcohol);
        alcoholRepository.save(alcohol);
        log.info("success");
        return alcohol;
    }

    public Alcohol update(Long id, Alcohol alcoholForm) {
        log.info("update alcohol. id: {}, alcohol: {}", id, alcoholForm);
        Alcohol alcohol = alcoholRepository.findById(id).orElseThrow();
        alcohol.setName(alcoholForm.getName());
        alcohol.setPrice(alcoholForm.getPrice());
        alcohol.setActive(alcoholForm.isActive());
        alcoholRepository.save(alcohol);
        log.info("success");
        return alcohol;
    }

    public void deleteById(Long id) {
        log.info("delete alcohol by id: {}", id);
        alcoholRepository.deleteById(id);

    }

    public Alcohol findById(Long id) {
        log.info("get alcohol by id: {}", id);
        Alcohol alcohol = alcoholRepository.findById(id).orElseThrow();
        log.info("success");
        return alcohol;
    }
}
