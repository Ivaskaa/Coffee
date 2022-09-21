package com.example.Coffee.service;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.city.City;
import com.example.Coffee.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;


@SpringBootTest
class CityServiceTest {
    @Autowired
    private CityService cityService;
    @MockBean
    private CityRepository cityRepository;

    @Test
    void findSortingPage() {
        cityService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(cityRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() {
        City city = new City();
        city.setName("city");
        city.setActive(true);
        city = cityService.save(city);
        Assertions.assertEquals("city", city.getName());
        Assertions.assertTrue(city.isActive());
        Mockito.verify(cityRepository, Mockito.times(1)).save(city);
    }

    @Test
    void update() {
        City city = new City();
        city.setName("city");
        city.setActive(true);

        Mockito.doReturn(Optional.of(new City()))
                .when(cityRepository)
                .findById(1L);

        city = cityService.update(1L, city);
        Assertions.assertEquals("city", city.getName());
        Assertions.assertTrue(city.isActive());
        Mockito.verify(cityRepository, Mockito.times(1)).save(city);
        Mockito.verify(cityRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        cityService.deleteById(1L);
        Mockito.verify(cityRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new City()))
                .when(cityRepository)
                .findById(1L);
        cityService.findById(1L);
        Mockito.verify(cityRepository, Mockito.times(1)).findById(1L);
    }
}