package com.example.Coffee.service;

import com.example.Coffee.entities.Location;
import com.example.Coffee.repository.LocationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
class LocationServiceTest {

    @Autowired
    private LocationService locationService;
    @MockBean
    private LocationRepository locationRepository;

//    @Test
//    void findAllActive() {
//        locationService.findAllActive();
//        Mockito.verify(locationRepository, Mockito.times(1)).findAllActive();
//    }

    @Test
    void findSortingPage() {
        locationService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(locationRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() {
        Location location = new Location();
        location.setLatitude(10d);
        location.setLongitude(10d);
        location.setName("name");
        location.setActive(true);
        Location checkedLocation = locationService.save(location);
        Assertions.assertEquals(location, checkedLocation);
        Mockito.verify(locationRepository, Mockito.times(1)).save(location);
    }

    @Test
    void update() {
        Location location = new Location();
        location.setLatitude(10d);
        location.setLongitude(10d);
        location.setName("name");
        location.setActive(true);

        Mockito.doReturn(Optional.of(new Location()))
                .when(locationRepository)
                .findById(1L);

        Location checkedLocation = locationService.update(1L, location);
        Assertions.assertEquals(location, checkedLocation);
        Mockito.verify(locationRepository, Mockito.times(1)).save(location);
        Mockito.verify(locationRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        locationService.deleteById(1L);
        Mockito.verify(locationRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Location()))
                .when(locationRepository)
                .findById(1L);
        locationService.findById(1L);
        Mockito.verify(locationRepository, Mockito.times(1)).findById(1L);
    }
}