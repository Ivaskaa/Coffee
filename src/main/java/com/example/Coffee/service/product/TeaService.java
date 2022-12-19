package com.example.Coffee.service.product;

import com.example.Coffee.entities.product.coffee.CoffeeSize;
import com.example.Coffee.entities.product.dessert.DessertSize;
import com.example.Coffee.entities.product.snack.Snack;
import com.example.Coffee.entities.product.tea.Tea;
import com.example.Coffee.entities.product.tea.TeaDto;
import com.example.Coffee.entities.product.tea.TeaSize;
import com.example.Coffee.repository.product.TeaRepository;
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
public class TeaService {
    private final TeaRepository teaRepository;
    private final StaticService service;

    public List<Tea> findAllActive(Long id){
        log.info("get all active teas");
        List<Tea> objects;
        if(id == null){
            objects = teaRepository.findAllByActiveTrue();
        } else {
            objects = teaRepository.findAllByActiveTrueOrId(id);
        }
        log.info("success");
        return objects;
    }

    public Page<Tea> findSortingPage(Integer currentPage, String sortingField, String sortingDirection){
        log.info("get tea page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Tea> coffees = teaRepository.findAll(pageable);
        log.info("success");
        return coffees;
    }

    public Tea save(Tea tea, MultipartFile file) throws IOException {
        log.info("add new tea. tea: {}, file: {}", tea, file);
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            tea.setPhoto(fileName);
            service.savePhoto("tea", file, fileName);
        }
        teaRepository.save(tea);
        log.info("success");
        return tea;
    }

    public Tea update(Long id, Tea teaForm, MultipartFile file) throws IOException {
        log.info("update tea: {}, file: {}", teaForm, file);
        Tea tea = teaRepository.findById(id).orElseThrow();
        String fileName;
        if(file != null && !file.getOriginalFilename().isEmpty()) {
            service.deletePhoto("tea", tea.getPhoto());
            fileName = (UUID.randomUUID() + "." + file.getOriginalFilename());
            teaForm.setPhoto(fileName);
            service.savePhoto("tea", file, fileName);
        }
        tea.setName(teaForm.getName());
        for(TeaSize teaSize : teaForm.getSizes()){
            teaSize.setTea(tea);
        }
        teaForm.setTeaOrders(tea.getTeaOrders());
        teaRepository.save(teaForm);
        log.info("success");
        return teaForm;
    }

    public void deleteById(Long id) throws FileNotFoundException {
        log.info("delete tea by id: {}", id);
        Tea tea = teaRepository.findById(id).orElseThrow();
        service.deletePhoto("tea", tea.getPhoto());
        teaRepository.deleteById(id);
        log.info("success");
    }

    public Tea findById(Long id) {
        log.info("get tea by id: {}", id);
        Tea tea = teaRepository.findById(id).orElseThrow();
        log.info("success");
        return tea;
    }

    public void teaSizesValidation(TeaDto teaDto, BindingResult bindingResult) {
        if(teaDto.getSizes() != null){
            if(!teaDto.getSizes().isEmpty()){
                teaDto.getSizes().sort((object1, object2) -> object1.getNumber().compareTo(object2.getNumber()));
                int i = 1;
                for(TeaSize size: teaDto.getSizes()){
                    for(TeaSize size2: teaDto.getSizes()){
                        if(!size.getNumber().equals(size2.getNumber())){
                            if(size.getName().equals(size2.getName())){
                                bindingResult.addError(new FieldError("teaDto", "sizeName" + i, "Must be unique for this product"));
                            }
                        }
                    }
                    if(!size.getName().equals("XS") &&
                            !size.getName().equals("S") &&
                            !size.getName().equals("M") &&
                            !size.getName().equals("L") &&
                            !size.getName().equals("XL")){
                        bindingResult.addError(new FieldError("teaDto", "sizeName" + i, "Must be (XS,S,M,L or XL)"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(size.getName())){
                        bindingResult.addError(new FieldError("teaDto", "sizeName" + i, "Must not be empty"));
                    }
                    if(StringUtils.isEmptyOrWhitespace(size.getDescription())){
                        bindingResult.addError(new FieldError("teaDto", "sizeDescription" + i, "Must not be empty"));
                    }
                    if(size.getPrice() == null){
                        bindingResult.addError(new FieldError("teaDto", "sizePrice" + i, "Must be number"));
                    }
                    if(size.getPrice() != null) {
                        if (size.getPrice() <= 0) {
                            bindingResult.addError(new FieldError("teaDto", "sizePrice" + i, "Must be greater than 0"));
                        }
                    }
                    i++;
                }
            }
        }
    }
}
