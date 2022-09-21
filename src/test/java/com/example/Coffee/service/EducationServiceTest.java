package com.example.Coffee.service;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Education;
import com.example.Coffee.repository.AdminRepository;
import com.example.Coffee.repository.EducationRepository;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EducationServiceTest {
    @Autowired
    private EducationService educationService;
    @MockBean
    private EducationRepository educationRepository;
    @MockBean
    private StaticService service;

    @Test
    void findSortingPage() {
        educationService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Mockito.verify(educationRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void save() throws IOException {
        Education education = new Education();
        education.setTitle("title");
        education.setText("text");
        education.setActive(true);

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        education = educationService.save(education, file);
        Assertions.assertEquals("title", education.getTitle());
        Assertions.assertEquals("text", education.getText());
        Assertions.assertNotEquals("photo.png", education.getPhoto());
        Mockito.verify(educationRepository, Mockito.times(1)).save(education);
        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("education"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename())
                );
    }

    @Test
    void update() throws IOException {
        Education education = new Education();
        education.setTitle("title");
        education.setText("text");
        education.setPhoto("photo.png");
        education.setActive(true);

        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        Mockito.doReturn(Optional.of(education))
                .when(educationRepository)
                .findById(1L);

        education = educationService.update(1L, education, file);
        Assertions.assertEquals("title", education.getTitle());
        Assertions.assertEquals("text", education.getText());
        Assertions.assertNotEquals("photo.png", education.getPhoto());
        Mockito.verify(educationRepository, Mockito.times(1)).save(education);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("education"),
                        ArgumentMatchers.contains(file.getOriginalFilename()));

        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("education"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename()));
    }

    @Test
    void deleteById() throws FileNotFoundException {
        Education education = new Education();
        education.setPhoto("photo.png");

        Mockito.doReturn(Optional.of(education))
                .when(educationRepository)
                .findById(1L);

        educationService.deleteById(1L);
        Mockito.verify(educationRepository, Mockito.times(1)).deleteById(1L);

        Mockito.verify(service, Mockito.times(1))
                .deletePhoto(
                        ArgumentMatchers.eq("education"),
                        ArgumentMatchers.contains(education.getPhoto()));
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Education()))
                .when(educationRepository)
                .findById(1L);
        educationService.findById(1L);
        Mockito.verify(educationRepository, Mockito.times(1)).findById(1L);
    }
}