package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.entities.product.dessert.DessertDto;
import com.example.Coffee.entities.product.dessert.DessertSize;
import com.example.Coffee.repository.product.DessertRepository;
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
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class DessertService {
    private final DessertRepository dessertRepository;
    private final StaticService service;

    public List<Dessert> findAllActive(Long id){
        log.info("get all active desserts");
        List<Dessert> desserts;
        if(id == null){
            desserts = dessertRepository.findAllByActiveTrue();
        } else {
            desserts = dessertRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return desserts;
    }

    public Page<Dessert> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get dessert page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Dessert> coffees = dessertRepository.findAll(pageable);
        log.info("success");
        return coffees;
    }

    public Dessert save(Dessert dessert, MultipartFile file) throws IOException {
        log.info("add new dessert. dessert: {}, file: {}", dessert, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            dessert.setPhoto(fileName);
            service.savePhoto("dessert", file, fileName);
        }
        dessertRepository.save(dessert);
        log.info("success");
        return dessert;
    }

    public Dessert update(Long id, Dessert dessertForm, MultipartFile file) throws IOException {
        log.info("update dessert: {}, file: {}", dessertForm, file);
        Dessert dessert = dessertRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("dessert", dessert.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            dessertForm.setPhoto(fileName);
            service.savePhoto("dessert", file, fileName);
        }
        for(DessertSize dessertSize : dessertForm.getSizes()){
            dessertSize.setDessert(dessert);
        }
        dessertForm.setDessertOrders(dessert.getDessertOrders());

        dessertRepository.save(dessertForm);
        log.info("success");
        return dessertForm;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete dessert by id: {}", id);
        Dessert dessert = dessertRepository.findById(id).orElseThrow();
        service.deletePhoto("dessert", dessert.getPhoto());
        dessertRepository.deleteById(id);
        log.info("success");
    }

    public Dessert findById(Long id) {
        log.info("get dessert by id: {}", id);
        Dessert dessert = dessertRepository.findById(id).orElseThrow();
        log.info("success");
        return dessert;
    }

    public void dessertSizesValidation(DessertDto dessertDto, BindingResult bindingResult) {
        if(dessertDto.getSizes() != null){
            if(!dessertDto.getSizes().isEmpty()){
                dessertDto.getSizes().sort((object1, object2) -> object1.getNumber().compareTo(object2.getNumber()));
                int i = 1;
                for(DessertSize size: dessertDto.getSizes()){
                    for(DessertSize size2: dessertDto.getSizes()){
                        if(!size.getNumber().equals(size2.getNumber())){
                            if(size.getName().equals(size2.getName())){
                                bindingResult.addError(new FieldError("dessertDto", "sizeName" + i, "Must be unique for this product"));
                            }
                        }
                    }
                    if(!size.getName().equals("XS") &&
                            !size.getName().equals("S") &&
                            !size.getName().equals("M") &&
                            !size.getName().equals("L") &&
                            !size.getName().equals("XL")){
                        bindingResult.addError(new FieldError("dessertDto", "sizeName" + i, "Must be (XS,S,M,L or XL)"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(size.getName())){
                        bindingResult.addError(new FieldError("dessertDto", "sizeName" + i, "Must not be empty"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(size.getDescription())){
                        bindingResult.addError(new FieldError("dessertDto", "sizeDescription" + i, "Must not be empty"));
                    }
                    if(size.getPrice() == null){
                        bindingResult.addError(new FieldError("dessertDto", "sizePrice" + i, "Must be number"));
                    }
                    if(size.getPrice() != null) {
                        if (size.getPrice() <= 0) {
                            bindingResult.addError(new FieldError("dessertDto", "sizePrice" + i, "Must be greater than 0"));
                        }
                    }
                    i++;
                }
            }
        }
    }
}
