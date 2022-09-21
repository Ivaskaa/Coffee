package com.example.Coffee.service;

import com.example.Coffee.entities.Education;
import com.example.Coffee.repository.EducationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;

    private final StaticService service;

    public Page<Education> findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        log.info("get educations page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 5, sort);
        Page<Education> educations = educationRepository.findAll(pageable);
        log.info("success");
        return educations;
    }

    public Education save(Education education, MultipartFile file) throws IOException {
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            education.setPhoto(fileName);
            service.savePhoto("education", file, fileName);
        }
        log.info("add new education: {}", education);
        educationRepository.save(education);
        log.info("success");
        return education;
    }

    public Education update(Long id, Education educationForm, MultipartFile file) throws IOException {
        Education education = educationRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("education", education.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            education.setPhoto(fileName);
            service.savePhoto("education", file, fileName);
        }
        education.setTitle(educationForm.getTitle());
        education.setText(educationForm.getText());
        education.setActive(educationForm.isActive());
        log.info("update education: {}", education);
        educationRepository.save(education);
        log.info("success");
        return education;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete education by id: {}", id);
        Education education = educationRepository.findById(id).orElseThrow();
        service.deletePhoto("education", education.getPhoto());
        educationRepository.deleteById(id);
        log.info("success");
    }

    public Education findById(Long id) {
        log.info("get education by id: {}", id);
        Education education = educationRepository.findById(id).orElseThrow();
        log.info("success");
        return education;
    }
}
