package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.entities.product.tea.Tea;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.product.TeaRepository;
import com.example.Coffee.service.StaticService;
import com.example.Coffee.service.product.TeaService;
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
class TeaServiceTest {
    @Autowired
    private TeaService teaService;
    @MockBean
    private TeaRepository teaRepository;
    @MockBean
    private StaticService service;

    @Test
    void findAllActive() {
        teaService.findAllActive();
        Mockito.verify(teaRepository, Mockito.times(1)).findAllActive();
    }

    @Test
    void findSortingPage() {
        teaService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(teaRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Tea tea = productFactory.getTea();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Tea checkedTea = teaService.save(tea, file);
        Assertions.assertEquals(checkedTea, tea);
        Mockito.verify(teaRepository, Mockito.times(1)).save(checkedTea);
        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("tea"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename())
                );
    }

    @Test
    void update() throws IOException {
        ProductFactory productFactory = new ProductFactory();
        Tea tea = productFactory.getTea();

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Mockito.doReturn(Optional.of(tea))
                .when(teaRepository)
                .findById(1L);

        Tea checkedTea = teaService.update(1L, tea, file);
        Assertions.assertEquals(checkedTea, tea);
        Mockito.verify(teaRepository, Mockito.times(1)).save(checkedTea);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("tea"),
                        ArgumentMatchers.contains(file.getOriginalFilename()));

        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("tea"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename()));
    }

    @Test
    void deleteById() throws FileNotFoundException {
        ProductFactory productFactory = new ProductFactory();
        Tea tea = productFactory.getTea();

        Mockito.doReturn(Optional.of(tea))
                .when(teaRepository)
                .findById(1L);

        teaService.deleteById(1L);
        Mockito.verify(teaRepository, Mockito.times(1)).deleteById(1L);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("tea"),
                        ArgumentMatchers.contains(tea.getPhoto()));
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Tea()))
                .when(teaRepository)
                .findById(1L);
        teaService.findById(1L);
        Mockito.verify(teaRepository, Mockito.times(1)).findById(1L);
    }
}