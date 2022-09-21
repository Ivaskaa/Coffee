package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.product.DessertRepository;
import com.example.Coffee.service.StaticService;
import com.example.Coffee.service.product.DessertService;
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
class DessertServiceTest {
    @Autowired
    private DessertService dessertService;
    @MockBean
    private DessertRepository dessertRepository;
    @MockBean
    private StaticService service;

    @Test
    void findAllActive() {
        dessertService.findAllActive();
        Mockito.verify(dessertRepository, Mockito.times(1)).findAllActive();
    }

    @Test
    void findSortingPage() {
        dessertService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(dessertRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Dessert dessert = productFactory.getDessert();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Dessert checkedDessert = dessertService.save(dessert, file);
        Assertions.assertEquals(checkedDessert, dessert);
        Mockito.verify(dessertRepository, Mockito.times(1)).save(dessert);
        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("dessert"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename())
                );
    }

    @Test
    void update() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Dessert dessert = productFactory.getDessert();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Mockito.doReturn(Optional.of(dessert))
                .when(dessertRepository)
                .findById(1L);

        Dessert checkedDessert = dessertService.update(1L, dessert, file);
        Assertions.assertEquals(checkedDessert, dessert);
        Mockito.verify(dessertRepository, Mockito.times(1)).save(checkedDessert);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("dessert"),
                        ArgumentMatchers.contains(file.getOriginalFilename()));

        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("dessert"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename()));
    }

    @Test
    void deleteById() throws FileNotFoundException {
        ProductFactory productFactory = new ProductFactory();
        Dessert dessert = productFactory.getDessert();

        Mockito.doReturn(Optional.of(dessert))
                .when(dessertRepository)
                .findById(1L);

        dessertService.deleteById(1L);
        Mockito.verify(dessertRepository, Mockito.times(1)).deleteById(1L);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("dessert"),
                        ArgumentMatchers.contains(dessert.getPhoto()));
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Dessert()))
                .when(dessertRepository)
                .findById(1L);
        dessertService.findById(1L);
        Mockito.verify(dessertRepository, Mockito.times(1)).findById(1L);
    }
}