package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.example.Coffee.entities.product.dessert.DessertSize;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.sandwich.SandwichDto;
import com.example.Coffee.entities.product.sandwich.SandwichSize;
import com.example.Coffee.repository.product.SandwichRepository;
import com.example.Coffee.service.StaticService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class SandwichService {
    private final SandwichRepository sandwichRepository;
    private final StaticService service;

    public List<Sandwich> findAllActive(Long id){
        log.info("get all active sandwiches");
        List<Sandwich> objects;
        if(id == null){
            objects = sandwichRepository.findAllByActiveTrue();
        } else {
            objects = sandwichRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Page<Sandwich> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get sandwich page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Sandwich> sandwiches = sandwichRepository.findAll(pageable);
        log.info("success");
        return sandwiches;
    }

    public Sandwich save(Sandwich sandwich, MultipartFile file) throws IOException {
        log.info("add new sandwich. sandwich: {}, file: {}", sandwich, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            sandwich.setPhoto(fileName);
            service.savePhoto("sandwich", file, fileName);
        }
        sandwichRepository.save(sandwich);
        log.info("success");
        return sandwich;
    }

    public Sandwich update(Long id, Sandwich sandwichForm, MultipartFile file) throws IOException {
        log.info("update sandwich: {}, file: {}", sandwichForm, file);
        Sandwich sandwich = sandwichRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("sandwich", sandwich.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            sandwichForm.setPhoto(fileName);
            service.savePhoto("sandwich", file, fileName);
        }
        sandwich.setName(sandwichForm.getName());

        for(SandwichSize sandwichSize : sandwichForm.getSizes()){
            sandwichSize.setSandwich(sandwich);
        }
        sandwichForm.setSandwichOrders(sandwich.getSandwichOrders());

        sandwichRepository.save(sandwichForm);
        log.info("success");
        return sandwichForm;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete sandwich by id: {}", id);
        Sandwich sandwich = sandwichRepository.findById(id).orElseThrow();
        service.deletePhoto("sandwich", sandwich.getPhoto());
        sandwichRepository.deleteById(id);
        log.info("success");
    }

    public Sandwich findById(Long id) {
        log.info("get sandwich by id: {}", id);
        Sandwich sandwich = sandwichRepository.findById(id).orElseThrow();
        log.info("success");
        return sandwich;
    }

    public void sandwichSizesValidation(SandwichDto sandwichDto, BindingResult bindingResult) {
        if(sandwichDto.getSizes() != null){
            if(!sandwichDto.getSizes().isEmpty()){
                sandwichDto.getSizes().sort((object1, object2) -> object1.getNumber().compareTo(object2.getNumber()));
                int i = 1;
                for(SandwichSize size: sandwichDto.getSizes()){
                    for(SandwichSize size2: sandwichDto.getSizes()){
                        if(!size.getNumber().equals(size2.getNumber())){
                            if(size.getName().equals(size2.getName())){
                                bindingResult.addError(new FieldError("sandwichDto", "sizeName" + i, "Must be unique for this product"));
                            }
                        }
                    }
                    if(!size.getName().equals("XS") &&
                            !size.getName().equals("S") &&
                            !size.getName().equals("M") &&
                            !size.getName().equals("L") &&
                            !size.getName().equals("XL")){
                        bindingResult.addError(new FieldError("sandwichDto", "sizeName" + i, "Must be (XS,S,M,L or XL)"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(size.getName())){
                        bindingResult.addError(new FieldError("sandwichDto", "sizeName" + i, "Must not be empty"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(size.getDescription())){
                        bindingResult.addError(new FieldError("sandwichDto", "sizeDescription" + i, "Must not be empty"));
                    }
                    if(size.getPrice() == null){
                        bindingResult.addError(new FieldError("sandwichDto", "sizePrice" + i, "Must be number"));
                    }
                    if(size.getPrice() != null) {
                        if (size.getPrice() <= 0) {
                            bindingResult.addError(new FieldError("sandwichDto", "sizePrice" + i, "Must be greater than 0"));
                        }
                    }
                    i++;
                }
            }
        }
    }
}
