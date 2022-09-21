package com.example.Coffee.service;

import com.example.Coffee.entities.SliderImage;
import com.example.Coffee.repository.SliderImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class SliderImageService {
    private final SliderImageRepository imageRepository;
    private final StaticService service;

    public List<SliderImage> findAll(){
        log.info("findAll sliderImage");
        List<SliderImage> sliderImages = imageRepository.findAll();
        log.info("success");
        return sliderImages;
    }

    public SliderImage save(MultipartFile file) throws IOException {
        log.info("add new sliderImage. file: {}", file);
        String fileName;
        SliderImage sliderImage = new SliderImage();
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            sliderImage.setImage(fileName);
            service.savePhoto("sliderImage", file, fileName);
        }
        imageRepository.save(sliderImage);
        log.info("success");
        return sliderImage;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete sliderImage by id: {}", id);
        SliderImage sliderImage = imageRepository.findById(id).orElseThrow();
        service.deletePhoto("sliderImage", sliderImage.getImage());
        imageRepository.deleteById(id);
        log.info("success");
    }
}
