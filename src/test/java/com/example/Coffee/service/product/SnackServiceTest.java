package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.product.SnackRepository;
import com.example.Coffee.service.StaticService;
import com.example.Coffee.service.product.SnackService;
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
class SnackServiceTest {
    @Autowired
    private SnackService snackService;
    @MockBean
    private SnackRepository snackRepository;
    @MockBean
    private StaticService service;

    @Test
    void findAllActive() {
        snackService.findAllActive();
        Mockito.verify(snackRepository, Mockito.times(1)).findAllActive();
    }

    @Test
    void findSortingPage() {
        snackService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(snackRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Snack snack = productFactory.getSnack();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Snack checkedSnack = snackService.save(snack, file);
        Assertions.assertEquals(checkedSnack, snack);
        Mockito.verify(snackRepository, Mockito.times(1)).save(checkedSnack);
        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("snack"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename())
                );
    }

    @Test
    void update() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Snack snack = productFactory.getSnack();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Mockito.doReturn(Optional.of(snack))
                .when(snackRepository)
                .findById(1L);

        Snack checkedSnack = snackService.update(1L, snack, file);
        Assertions.assertEquals(checkedSnack, snack);
        Mockito.verify(snackRepository, Mockito.times(1)).save(checkedSnack);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("snack"),
                        ArgumentMatchers.contains(file.getOriginalFilename()));

        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("snack"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename()));
    }

    @Test
    void deleteById() throws FileNotFoundException {
        ProductFactory productFactory = new ProductFactory();
        Snack snack = productFactory.getSnack();

        Mockito.doReturn(Optional.of(snack))
                .when(snackRepository)
                .findById(1L);

        snackService.deleteById(1L);
        Mockito.verify(snackRepository, Mockito.times(1)).deleteById(1L);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("snack"),
                        ArgumentMatchers.contains(snack.getPhoto()));
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Snack()))
                .when(snackRepository)
                .findById(1L);
        snackService.findById(1L);
        Mockito.verify(snackRepository, Mockito.times(1)).findById(1L);
    }
}