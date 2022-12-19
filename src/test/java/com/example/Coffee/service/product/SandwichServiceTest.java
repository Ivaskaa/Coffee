package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.product.SandwichRepository;
import com.example.Coffee.service.StaticService;
import com.example.Coffee.service.product.SandwichService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@SpringBootTest
class SandwichServiceTest {
    @Autowired
    private SandwichService sandwichService;
    @MockBean
    private SandwichRepository sandwichRepository;
    @MockBean
    private StaticService service;

//    @Test
//    void findAllActive() {
//        sandwichService.findAllActive();
//        Mockito.verify(sandwichRepository, Mockito.times(1)).findAllActive();
//    }

    @Test
    void findSortingPage() {
        sandwichService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(sandwichRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Sandwich sandwich = productFactory.getSandwich();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Sandwich checkedSandwich = sandwichService.save(sandwich, file);
        Assertions.assertEquals(checkedSandwich, sandwich);
        Mockito.verify(sandwichRepository, Mockito.times(1)).save(checkedSandwich);
        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("sandwich"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename())
                );
    }

    @Test
    void update() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Sandwich sandwich = productFactory.getSandwich();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Mockito.doReturn(Optional.of(sandwich))
                .when(sandwichRepository)
                .findById(1L);

        Sandwich checkedSandwich = sandwichService.update(1L, sandwich, file);
        Assertions.assertEquals(checkedSandwich, sandwich);
        Mockito.verify(sandwichRepository, Mockito.times(1)).save(checkedSandwich);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("sandwich"),
                        ArgumentMatchers.contains(file.getOriginalFilename()));

        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("sandwich"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename()));
    }

    @Test
    void deleteById() throws FileNotFoundException {
        ProductFactory productFactory = new ProductFactory();
        Sandwich sandwich = productFactory.getSandwich();

        Mockito.doReturn(Optional.of(sandwich))
                .when(sandwichRepository)
                .findById(1L);

        sandwichService.deleteById(1L);
        Mockito.verify(sandwichRepository, Mockito.times(1)).deleteById(1L);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("sandwich"),
                        ArgumentMatchers.contains(sandwich.getPhoto()));
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Sandwich()))
                .when(sandwichRepository)
                .findById(1L);
        sandwichService.findById(1L);
        Mockito.verify(sandwichRepository, Mockito.times(1)).findById(1L);
    }
}