package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.entities.product.coffee.CoffeeDto;
import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.example.Coffee.entities.product.dessert.Dessert;
import com.example.Coffee.repository.product.CoffeeRepository;
import com.example.Coffee.service.StaticService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import java.util.stream.Collectors;


@Service
@Log4j2
@AllArgsConstructor
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    private final StaticService service;

    public List<Coffee> findAllActive(Long id){
        log.info("get all active coffees");
        List<Coffee> objects;
        if(id == null){
            objects = coffeeRepository.findAllByActiveTrue();
        } else {
            objects = coffeeRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Page<Coffee> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get coffee page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Coffee> coffees = coffeeRepository.findAll(pageable);
        log.info("success");
        return coffees;
    }

    public Coffee save(Coffee coffee, MultipartFile file) throws IOException {
        log.info("add new coffee. coffee: {}, file: {}", coffee, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            coffee.setPhoto(fileName);
            service.savePhoto("coffee", file, fileName);
        }
        coffeeRepository.save(coffee);
        log.info("success");
        return coffee;
    }

    public Coffee update(Long id, Coffee coffeeForm, MultipartFile file) throws IOException {
        log.info("update coffee: {}, file: {}", coffeeForm, file);
        Coffee coffee = coffeeRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("coffee", coffee.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            coffeeForm.setPhoto(fileName);
            service.savePhoto("coffee", file, fileName);
        }
        for(CoffeeSize coffeeSize : coffeeForm.getSizes()){
            coffeeSize.setCoffee(coffee);
        }
        coffeeForm.setCoffeeOrders(coffee.getCoffeeOrders());
        coffeeRepository.save(coffeeForm);
        log.info("success");
        return coffeeForm;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        Coffee coffee = coffeeRepository.findById(id).orElseThrow();
        service.deletePhoto("coffee", coffee.getPhoto());
        coffeeRepository.deleteById(id);
        log.info("delete coffee by id: {}", id);
    }

    public Coffee findById(Long id) {
        Coffee coffee = coffeeRepository.findById(id).orElseThrow();
        System.out.println(coffee);
        log.info("get coffee by id: {}", id);
        return coffee;
    }

    public void coffeeSizesValidation(CoffeeDto coffeeDto, BindingResult bindingResult) {
        if(coffeeDto.getSizes() != null){
            if(!coffeeDto.getSizes().isEmpty()){
                System.out.println(coffeeDto);
                coffeeDto.getSizes().sort((object1, object2) -> object1.getNumber().compareTo(object2.getNumber()));
                System.out.println(coffeeDto);
                int i = 1;
                for(CoffeeSize coffeeSize: coffeeDto.getSizes()){
                    for(CoffeeSize coffeeSize2: coffeeDto.getSizes()){
                        if(!coffeeSize.getNumber().equals(coffeeSize2.getNumber())){
                            if(coffeeSize.getName().equals(coffeeSize2.getName())){
                                bindingResult.addError(new FieldError("coffeeDto", "sizeName" + i, "Must be unique for this product"));
                            }
                        }
                    }
                    if(!coffeeSize.getName().equals("XS") &&
                            !coffeeSize.getName().equals("S") &&
                            !coffeeSize.getName().equals("M") &&
                            !coffeeSize.getName().equals("L") &&
                            !coffeeSize.getName().equals("XL")){
                        bindingResult.addError(new FieldError("coffeeDto", "sizeName" + i, "Must be (XS,S,M,L or XL)"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(coffeeSize.getName())){
                        bindingResult.addError(new FieldError("coffeeDto", "sizeName" + i, "Must not be empty"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(coffeeSize.getDescription())){
                        bindingResult.addError(new FieldError("coffeeDto", "sizeDescription" + i, "Must not be empty"));
                    }
                    if(coffeeSize.getPrice() == null){
                        bindingResult.addError(new FieldError("coffeeDto", "sizePrice" + i, "Must be number"));
                    }
                    if(coffeeSize.getPrice() != null) {
                        if (coffeeSize.getPrice() <= 0) {
                            bindingResult.addError(new FieldError("coffeeDto", "sizePrice" + i, "Must be greater than 0"));
                        }
                    }
                    i++;
                }
            }
        }
    }
}
