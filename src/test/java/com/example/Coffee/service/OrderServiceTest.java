package com.example.Coffee.service;

import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.util.Optional;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;

    @Test
    void findSortingAndSpecificationPage() throws ParseException {
        orderService.findSortingAndSpecificationPage(
                1,
                "id",
                "ASC",
                "1",
                "name",
                "date", "sd",
                "time", "sdf",
                "city",
                "home",
                "entrance",
                "flat"
        );
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(orderRepository, Mockito.times(1))
                .findAll(
                        ArgumentMatchers.isA(Specification.class),  // клас специфікацій не має переоприділенного equals
                        ArgumentMatchers.eq(pageable));
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Order()))
                .when(orderRepository)
                .findById(1L);
        orderService.findById(1L);
        Mockito.verify(orderRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        orderService.deleteById(1L);
        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(1L);
    }
}