package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.repository.ingredients.AlcoholRepository;
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
class AlcoholServiceTest {
    @Autowired
    private AlcoholService alcoholService;
    @MockBean
    private AlcoholRepository alcoholRepository;

    @Test
    void findAllActive() {
        alcoholService.findAllActive();
        Mockito.verify(alcoholRepository, Mockito.times(1)).findAllActive();
    }

    @Test
    void findSortingPage() {
        alcoholService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(alcoholRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Alcohol alcohol = ingredientsFactory.getAlcohol();

        Alcohol checkedAlcohol = alcoholService.save(alcohol);
        Assertions.assertEquals(checkedAlcohol, alcohol);
        Mockito.verify(alcoholRepository, Mockito.times(1)).save(alcohol);
    }

    @Test
    void update() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Alcohol alcohol = ingredientsFactory.getAlcohol();

        Mockito.doReturn(Optional.of(new Alcohol()))
                .when(alcoholRepository)
                .findById(1L);

        Alcohol checkedAlcohol = alcoholService.update(1L, alcohol);
        Assertions.assertEquals(checkedAlcohol, alcohol);
        Mockito.verify(alcoholRepository, Mockito.times(1)).save(alcohol);
        Mockito.verify(alcoholRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        alcoholService.deleteById(1L);
        Mockito.verify(alcoholRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Alcohol()))
                .when(alcoholRepository)
                .findById(1L);
        alcoholService.findById(1L);
        Mockito.verify(alcoholRepository, Mockito.times(1)).findById(1L);
    }
}