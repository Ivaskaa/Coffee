package com.example.Coffee.service;

import com.example.Coffee.entities.Location;
import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.repository.LocationRepository;
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
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> findAllActive(Long id) {
        log.info("get all active locations");
        List<Location> objects;
        if(id == null) {
            objects = locationRepository.findAllByActiveTrue();
        } else {
            objects = locationRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Object findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        log.info("get location page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Location> feedbackPage = locationRepository.findAll(pageable);
        log.info("success");
        return feedbackPage;
    }

    public Location save(Location location) {
        log.info("save location: {}", location);
        locationRepository.save(location);
        log.info("success");
        return location;
    }

    public Location update(Long id, Location locationForm) {
        log.info("update location. id: {}, location: {}", id, locationForm);
        Location location = locationRepository.findById(id).orElseThrow();
        location.setName(locationForm.getName());
        location.setLatitude(locationForm.getLatitude());
        location.setLongitude(locationForm.getLongitude());
        location.setActive(locationForm.isActive());
        locationRepository.save(location);
        log.info("success");
        return location;
    }

    public void deleteById(Long id) {
        log.info("delete location by id: {}", id);
        locationRepository.deleteById(id);
        log.info("success");
    }

    public Location findById(Long id) {
        log.info("get feedback by id: {}", id);
        Location location = locationRepository.findById(id).orElseThrow();
        log.info("success");
        return location;
    }
}
