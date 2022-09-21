package com.example.Coffee.service.ingredient;

import com.example.Coffee.entities.ingredients.sauce.Sauce;
import com.example.Coffee.entities.ingredients.supplement.Supplement;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.repository.ingredients.SupplementRepository;
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
class SupplementServiceTest {

    @Autowired
    private SupplementService supplementService;
    @MockBean
    private SupplementRepository supplementRepository;

    @Test
    void findAllActive() {
        supplementService.findAllActive();
        Mockito.verify(supplementRepository, Mockito.times(1)).findAllActive();
    }

    @Test
    void findSortingPage() {
        supplementService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(supplementRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Supplement supplement = ingredientsFactory.getSupplement();

        Supplement checkedSupplement = supplementService.save(supplement);
        Assertions.assertEquals(checkedSupplement, supplement);
        Mockito.verify(supplementRepository, Mockito.times(1)).save(supplement);
    }

    @Test
    void update() {
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        Supplement supplement = ingredientsFactory.getSupplement();

        Mockito.doReturn(Optional.of(new Supplement()))
                .when(supplementRepository)
                .findById(1L);

        Supplement checkedSupplement = supplementService.update(1L, supplement);
        Assertions.assertEquals(checkedSupplement, supplement);
        Mockito.verify(supplementRepository, Mockito.times(1)).save(supplement);
        Mockito.verify(supplementRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        supplementService.deleteById(1L);
        Mockito.verify(supplementRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Supplement()))
                .when(supplementRepository)
                .findById(1L);
        supplementService.findById(1L);
        Mockito.verify(supplementRepository, Mockito.times(1)).findById(1L);
    }
}