package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.example.Coffee.entities.product.dessert.DessertSize;
import com.example.Coffee.entities.product.sandwich.Sandwich;
import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.entities.product.snack.SnackDto;
import com.example.Coffee.entities.product.snack.SnackSize;
import com.example.Coffee.repository.product.SnackRepository;
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
public class SnackService {
    private final SnackRepository snackRepository;
    private final StaticService service;

    public List<Snack> findAllActive(Long id){
        log.info("get all active snacks");
        List<Snack> objects;
        if(id == null){
            objects = snackRepository.findAllByActiveTrue();
        } else {
            objects = snackRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Page<Snack> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get snack page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Snack> snacks = snackRepository.findAll(pageable);
        log.info("success");
        return snacks;
    }

    public Snack save(Snack snack, MultipartFile file) throws IOException {
        log.info("add new snack. snack: {}, file: {}", snack, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            snack.setPhoto(fileName);
            service.savePhoto("snack", file, fileName);
        }
        snackRepository.save(snack);
        log.info("success");
        return snack;
    }

    public Snack update(Long id, Snack snackForm, MultipartFile file) throws IOException {
        log.info("update snack: {}, file: {}", snackForm, file);
        Snack snack = snackRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("snack", snack.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            snackForm.setPhoto(fileName);
            service.savePhoto("snack", file, fileName);
        }
        for(SnackSize snackSize : snackForm.getSizes()){
            snackSize.setSnack(snack);
        }
        snackForm.setSnackOrders(snack.getSnackOrders());
        snackRepository.save(snackForm);
        log.info("success");
        return snackForm;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete snack by id: {}", id);
        Snack snack = snackRepository.findById(id).orElseThrow();
        service.deletePhoto("snack", snack.getPhoto());
        snackRepository.deleteById(id);
        log.info("success");
    }

    public Snack findById(Long id) {
        log.info("get snack by id: {}", id);
        Snack snack = snackRepository.findById(id).orElseThrow();
        log.info("success");
        return snack;
    }

    public void snackSizesValidation(SnackDto snackDto, BindingResult bindingResult) {
        if(snackDto.getSizes() != null){
            if(!snackDto.getSizes().isEmpty()){
                snackDto.getSizes().sort((object1, object2) -> object1.getNumber().compareTo(object2.getNumber()));
                int i = 1;
                for(SnackSize size: snackDto.getSizes()){
                    for(SnackSize size2: snackDto.getSizes()){
                        if(!size.getNumber().equals(size2.getNumber())){
                            if(size.getName().equals(size2.getName())){
                                bindingResult.addError(new FieldError("snackDto", "sizeName" + i, "Must be unique for this product"));
                            }
                        }
                    }
                    if(!size.getName().equals("XS") &&
                            !size.getName().equals("S") &&
                            !size.getName().equals("M") &&
                            !size.getName().equals("L") &&
                            !size.getName().equals("XL")){
                        bindingResult.addError(new FieldError("snackDto", "sizeName" + i, "Must be (XS,S,M,L or XL)"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(size.getName())){
                        bindingResult.addError(new FieldError("snackDto", "sizeName" + i, "Must not be empty"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(size.getDescription())){
                        bindingResult.addError(new FieldError("snackDto", "sizeDescription" + i, "Must not be empty"));
                    }
                    if(size.getPrice() == null){
                        bindingResult.addError(new FieldError("snackDto", "sizePrice" + i, "Must be number"));
                    }
                    if(size.getPrice() != null) {
                        if (size.getPrice() <= 0) {
                            bindingResult.addError(new FieldError("snackDto", "sizePrice" + i, "Must be greater than 0"));
                        }
                    }
                    i++;
                }
            }
        }
    }
}
