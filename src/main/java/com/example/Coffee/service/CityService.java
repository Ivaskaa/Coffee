package com.example.Coffee.service;


import com.example.Coffee.entities.city.City;
import com.example.Coffee.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public Object findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        log.info("get city page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<City> feedbackPage = cityRepository.findAll(pageable);
        log.info("success");
        return feedbackPage;
    }

    public City save(City city) {
        log.info("save city: {}", city);
        cityRepository.save(city);
        log.info("success");
        return city;
    }

    public City update(Long id, City cityFrom) {
        log.info("update city. id: {}, city: {}", id, cityFrom);
        City city = cityRepository.findById(id).orElseThrow();
        city.setName(cityFrom.getName());
        city.setActive(cityFrom.isActive());
        cityRepository.save(city);
        log.info("success");
        return city;
    }

    public void deleteById(Long id) {
        log.info("delete city by id: {}", id);
        cityRepository.deleteById(id);
        log.info("success");
    }

    public City findById(Long id) {
        log.info("get city by id: {}", id);
        City city = cityRepository.findById(id).orElseThrow();
        log.info("success");
        return city;
    }
}