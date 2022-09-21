package com.example.Coffee.service.order;

import com.example.Coffee.entities.order.dessert.DessertOrder;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.others.OrderFactory;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.orderDetails.DessertOrderRepository;
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
class DessertOrderServiceTest {
    @Autowired
    private DessertOrderService dessertOrderService;
    @MockBean
    private DessertOrderRepository dessertOrderRepository;

    @Test
    void findPageByOrderId() {
        dessertOrderService.findPageByOrderId(1, "id", "ASC", 1L);
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(dessertOrderRepository, Mockito.times(1)).findAllByOrderId(1L, pageable);
    }

    @Test
    void save() {
        ProductFactory productFactory = new ProductFactory();
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        OrderFactory orderFactory = new OrderFactory(productFactory, ingredientsFactory);
        DessertOrder dessertOrder = orderFactory.getDessertOrder();

        Mockito.doReturn(Optional.of(new Order()))
                .when(dessertOrderRepository)
                .findById(1L);

        DessertOrder checkedDessertOrder = dessertOrderService.save(dessertOrder);

        Assertions.assertEquals(checkedDessertOrder, dessertOrder);
        Mockito.verify(dessertOrderRepository, Mockito.times(1)).save(checkedDessertOrder);
    }

    @Test
    void update() {
        ProductFactory productFactory = new ProductFactory();
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        OrderFactory orderFactory = new OrderFactory(productFactory, ingredientsFactory);
        DessertOrder dessertOrder = orderFactory.getDessertOrder();

        Mockito.doReturn(Optional.of(new Order()))
                .when(dessertOrderRepository)
                .findById(1L);

        DessertOrder checkedDessertOrder = dessertOrderService.save(dessertOrder);

        Assertions.assertEquals(checkedDessertOrder, dessertOrder);
        Mockito.verify(dessertOrderRepository, Mockito.times(1)).save(dessertOrder);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new DessertOrder()))
                .when(dessertOrderRepository)
                .findById(1L);
        dessertOrderService.findById(1L);
        Mockito.verify(dessertOrderRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        dessertOrderService.deleteById(1L);
        Mockito.verify(dessertOrderRepository, Mockito.times(1)).deleteById(1L);
    }
}