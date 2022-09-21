package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.alcohol.Alcohol;
import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.repository.ingredients.AlcoholRepository;
import com.example.Coffee.repository.ingredients.MilkRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MilkServiceTest {
    @Autowired
    private MilkService milkService;
    @MockBean
    private MilkRepository milkRepository;

    @Test
    void findAllActive() {
        milkService.findAllActive();
        Mockito.verify(milkRepository, Mockito.times(1)).findAllActive();
    }

    @Test
    void findSortingPage() {
        milkService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(milkRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void saveMilk() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Milk milk = ingredientsFactory.getMilk();

        Milk checkedMilk = milkService.save(milk);
        Assertions.assertEquals(checkedMilk, milk);
        Mockito.verify(milkRepository, Mockito.times(1)).save(milk);
    }

    @Test
    void updateMilk() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Milk milk = ingredientsFactory.getMilk();

        Mockito.doReturn(Optional.of(new Milk()))
                .when(milkRepository)
                .findById(1L);

        Milk checkedMilk = milkService.update(1L, milk);
        Assertions.assertEquals(checkedMilk, milk);
        Mockito.verify(milkRepository, Mockito.times(1)).save(milk);
        Mockito.verify(milkRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        milkService.deleteById(1L);
        Mockito.verify(milkRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Milk()))
                .when(milkRepository)
                .findById(1L);
        milkService.findById(1L);
        Mockito.verify(milkRepository, Mockito.times(1)).findById(1L);
    }
}