package com.example.Coffee.service.order;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.product.coffee.Coffee;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.others.OrderFactory;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.orderDetails.CoffeeOrderRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoffeeOrderServiceTest {
    @Autowired
    private CoffeeOrderService coffeeOrderService;
    @MockBean
    private CoffeeOrderRepository coffeeOrderRepository;

    @Test
    void findPageByOrderId() {
        coffeeOrderService.findPageByOrderId(1, "id", "ASC", 1L);
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(coffeeOrderRepository, Mockito.times(1)).findAllByOrderId(1L, pageable);
    }

    @Test
    void save() {
        ProductFactory productFactory = new ProductFactory();
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        OrderFactory orderFactory = new OrderFactory(productFactory, ingredientsFactory);
        CoffeeOrder coffeeOrder = orderFactory.getCoffeeOrder();

        Mockito.doReturn(Optional.of(new Order()))
                .when(coffeeOrderRepository)
                .findById(1L);

        CoffeeOrder checkedCoffeeOrder = coffeeOrderService.save(coffeeOrder);

        Assertions.assertEquals(checkedCoffeeOrder, coffeeOrder);
        Mockito.verify(coffeeOrderRepository, Mockito.times(1)).save(coffeeOrder);
    }

    @Test
    void update() {
        ProductFactory productFactory = new ProductFactory();
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        OrderFactory orderFactory = new OrderFactory(productFactory, ingredientsFactory);
        CoffeeOrder coffeeOrder = orderFactory.getCoffeeOrder();

        Mockito.doReturn(Optional.of(new Order()))
                .when(coffeeOrderRepository)
                .findById(1L);

        CoffeeOrder checkedCoffeeOrder = coffeeOrderService.save(coffeeOrder);

        Assertions.assertEquals(checkedCoffeeOrder, coffeeOrder);
        Mockito.verify(coffeeOrderRepository, Mockito.times(1)).save(coffeeOrder);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new CoffeeOrder()))
                .when(coffeeOrderRepository)
                .findById(1L);
        coffeeOrderService.findById(1L);
        Mockito.verify(coffeeOrderRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        coffeeOrderService.deleteById(1L);
        Mockito.verify(coffeeOrderRepository, Mockito.times(1)).deleteById(1L);
    }
}