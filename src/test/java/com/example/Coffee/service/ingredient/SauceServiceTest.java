package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.milk.Milk;
import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.repository.ingredients.SauceRepository;
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
class SauceServiceTest {
    @Autowired
    private SauceService sauceService;
    @MockBean
    private SauceRepository sauceRepository;

//    @Test
//    void findAllActive() {
//        sauceService.findAllActive();
//        Mockito.verify(sauceRepository, Mockito.times(1)).findAllActive();
//    }

    @Test
    void findSortingPage() {
        sauceService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(sauceRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Sauce sauce = ingredientsFactory.getSauce();

        Sauce checkedSauce = sauceService.save(sauce);
        Assertions.assertEquals(checkedSauce, sauce);
        Mockito.verify(sauceRepository, Mockito.times(1)).save(sauce);
    }

    @Test
    void update() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Sauce sauce = ingredientsFactory.getSauce();

        Mockito.doReturn(Optional.of(new Sauce()))
                .when(sauceRepository)
                .findById(1L);

        Sauce checkedSauce = sauceService.update(1L, sauce);
        Assertions.assertEquals(checkedSauce, sauce);
        Mockito.verify(sauceRepository, Mockito.times(1)).save(sauce);
        Mockito.verify(sauceRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        sauceService.deleteById(1L);
        Mockito.verify(sauceRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Sauce()))
                .when(sauceRepository)
                .findById(1L);
        sauceService.findById(1L);
        Mockito.verify(sauceRepository, Mockito.times(1)).findById(1L);
    }
}