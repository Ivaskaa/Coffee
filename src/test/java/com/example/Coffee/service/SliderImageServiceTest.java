package com.example.Coffee.service;

import com.example.Coffee.entities.Location;
import com.example.Coffee.entities.SliderImage;
import com.example.Coffee.repository.LocationRepository;
import com.example.Coffee.repository.SliderImageRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SliderImageServiceTest {

    @Autowired
    private SliderImageService imageService;
    @MockBean
    private SliderImageRepository imageRepository;
    @MockBean
    private StaticService service;

    @Test
    void findAll() {
        imageService.findAll();
        Mockito.verify(imageRepository, Mockito.times(1)).findAll();
    }

    @Test
    void saveSliderImage() throws IOException {
        MultipartFile file = new MockMultipartFile(
                "photo.png",
                "photo.png",
                "contextType",
                InputStream.nullInputStream());

        SliderImage sliderImage = imageService.save(file);
        Mockito.verify(imageRepository, Mockito.times(1)).save(sliderImage);
        Mockito.verify(service, Mockito.times(1))
                .savePhoto(
                        ArgumentMatchers.eq("sliderImage"),
                        ArgumentMatchers.eq(file),
                        ArgumentMatchers.contains(file.getOriginalFilename())
                );
    }

    @Test
    void deleteById() throws FileNotFoundException {
        SliderImage image = new SliderImage();
        image.setImage("photo.png");

        Mockito.doReturn((Optional.of(image)))
                .when(imageRepository)
                .findById(1L);

        imageService.deleteById(1L);
        Mockito.verify(imageRepository, Mockito.times(1)).deleteById(1L);
    }
}