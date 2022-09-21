package com.example.Coffee.service.order;

import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.others.OrderFactory;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.orderDetails.SandwichOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
@SpringBootTest
class SandwichOrderServiceTest {
    @Autowired
    private SandwichOrderService sandwichOrderService;
    @MockBean
    private SandwichOrderRepository sandwichOrderRepository;

    @Test
    void findPageByOrderId() {
        sandwichOrderService.findPageByOrderId(1, "id", "ASC", 1L);
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(sandwichOrderRepository, Mockito.times(1)).findAllByOrderId(1L, pageable);
    }

    @Test
    void save() {
        ProductFactory productFactory = new ProductFactory();
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        OrderFactory orderFactory = new OrderFactory(productFactory, ingredientsFactory);
        SandwichOrder productOrder = orderFactory.getSandwichOrder();

        Mockito.doReturn(Optional.of(new Order()))
                .when(sandwichOrderRepository)
                .findById(1L);

        SandwichOrder checkedProductOrder = sandwichOrderService.save(productOrder);

        Assertions.assertEquals(checkedProductOrder, productOrder);
        Mockito.verify(sandwichOrderRepository, Mockito.times(1)).save(checkedProductOrder);
    }

    @Test
    void update() {
        ProductFactory productFactory = new ProductFactory();
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        OrderFactory orderFactory = new OrderFactory(productFactory, ingredientsFactory);
        SandwichOrder productOrder = orderFactory.getSandwichOrder();

        Mockito.doReturn(Optional.of(new Order()))
                .when(sandwichOrderRepository)
                .findById(1L);

        SandwichOrder checkedProductOrder = sandwichOrderService.save(productOrder);

        Assertions.assertEquals(checkedProductOrder, productOrder);
        Mockito.verify(sandwichOrderRepository, Mockito.times(1)).save(productOrder);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new SandwichOrder()))
                .when(sandwichOrderRepository)
                .findById(1L);
        sandwichOrderService.findById(1L);
        Mockito.verify(sandwichOrderRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        sandwichOrderService.deleteById(1L);
        Mockito.verify(sandwichOrderRepository, Mockito.times(1)).deleteById(1L);
    }
}