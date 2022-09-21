package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.product.CoffeeRepository;
import com.example.Coffee.service.StaticService;
import com.example.Coffee.service.product.CoffeeService;
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
class CoffeeServiceTest {
    @Autowired
    private CoffeeService coffeeService;
    @MockBean
    private CoffeeRepository coffeeRepository;
    @MockBean
    private StaticService service;

    @Test
    void findAllActive() {
        coffeeService.findAllActive();
        Mockito.verify(coffeeRepository, Mockito.times(1)).findAllActive();
    }

    @Test
    void findSortingPage() {
        coffeeService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(coffeeRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Coffee coffee = productFactory.getCoffee();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Coffee checkedCoffee = coffeeService.save(coffee, file);
        Assertions.assertEquals(checkedCoffee, coffee);
        Mockito.verify(coffeeRepository, Mockito.times(1)).save(checkedCoffee);
        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("coffee"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename())
                );
    }

    @Test
    void update() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Coffee coffee = productFactory.getCoffee();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Mockito.doReturn(Optional.of(coffee))
                .when(coffeeRepository)
                .findById(1L);

        Coffee checkedCoffee = coffeeService.update(1L, coffee, file);
        Assertions.assertEquals(checkedCoffee, coffee);
        Mockito.verify(coffeeRepository, Mockito.times(1)).save(checkedCoffee);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("coffee"),
                        ArgumentMatchers.contains(file.getOriginalFilename()));

        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("coffee"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename()));
    }

    @Test
    void deleteById() throws FileNotFoundException {
        ProductFactory productFactory = new ProductFactory();
        Coffee coffee = productFactory.getCoffee();

        Mockito.doReturn(Optional.of(coffee))
                .when(coffeeRepository)
                .findById(1L);

        coffeeService.deleteById(1L);
        Mockito.verify(coffeeRepository, Mockito.times(1)).deleteById(1L);
        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("coffee"),
                        ArgumentMatchers.contains(coffee.getPhoto()));
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Coffee()))
                .when(coffeeRepository)
                .findById(1L);
        coffeeService.findById(1L);
        Mockito.verify(coffeeRepository, Mockito.times(1)).findById(1L);
    }
}