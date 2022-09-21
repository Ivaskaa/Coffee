package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.syrup.Syrup;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.repository.ingredients.SyrupRepository;
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
class SyrupServiceTest {
    @Autowired
    private SyrupService syrupService;
    @MockBean
    private SyrupRepository syrupRepository;

    @Test
    void findAllActive() {
        syrupService.findAllActive();
        Mockito.verify(syrupRepository, Mockito.times(1)).findAllActive();
    }

    @Test
    void findSortingPage() {
        syrupService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(syrupRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Syrup syrup = ingredientsFactory.getSyrup();

        Syrup checkedSyrup = syrupService.save(syrup);
        Assertions.assertEquals(checkedSyrup, syrup);
        Mockito.verify(syrupRepository, Mockito.times(1)).save(syrup);
    }

    @Test
    void update() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Syrup syrup = ingredientsFactory.getSyrup();

        Mockito.doReturn(Optional.of(new Syrup()))
                .when(syrupRepository)
                .findById(1L);

        Syrup checkedSyrup = syrupService.update(1L, syrup);
        Assertions.assertEquals(checkedSyrup, syrup);
        Mockito.verify(syrupRepository, Mockito.times(1)).save(syrup);
        Mockito.verify(syrupRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        syrupService.deleteById(1L);
        Mockito.verify(syrupRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Syrup()))
                .when(syrupRepository)
                .findById(1L);
        syrupService.findById(1L);
        Mockito.verify(syrupRepository, Mockito.times(1)).findById(1L);
    }
}