package com.example.Coffee.service.order;

import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.order.snack.SnackOrder;
import com.example.Coffee.others.IngredientsFactory;
import com.example.Coffee.others.OrderFactory;
import com.example.Coffee.others.ProductFactory;
import com.example.Coffee.repository.orderDetails.SnackOrderRepository;
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
class SnackOrderServiceTest {
    @Autowired
    private SnackOrderService snackOrderService;
    @MockBean
    private SnackOrderRepository snackOrderRepository;

    @Test
    void findPageByOrderId() {
        snackOrderService.findPageByOrderId(1, "id", "ASC", 1L);
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(snackOrderRepository, Mockito.times(1)).findAllByOrderId(1L, pageable);
    }

    @Test
    void save() {
        ProductFactory productFactory = new ProductFactory();
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        OrderFactory orderFactory = new OrderFactory(productFactory, ingredientsFactory);
        SnackOrder productOrder = orderFactory.getSnackOrder();

        Mockito.doReturn(Optional.of(new Order()))
                .when(snackOrderRepository)
                .findById(1L);

        SnackOrder checkedProductOrder = snackOrderService.save(productOrder);

        Assertions.assertEquals(checkedProductOrder, productOrder);
        Mockito.verify(snackOrderRepository, Mockito.times(1)).save(checkedProductOrder);
    }

    @Test
    void update() {
        ProductFactory productFactory = new ProductFactory();
        IngredientsFactory ingredientsFactory = new IngredientsFactory();
        OrderFactory orderFactory = new OrderFactory(productFactory, ingredientsFactory);
        SnackOrder productOrder = orderFactory.getSnackOrder();

        Mockito.doReturn(Optional.of(new Order()))
                .when(snackOrderRepository)
                .findById(1L);

        SnackOrder checkedProductOrder = snackOrderService.save(productOrder);

        Assertions.assertEquals(checkedProductOrder, productOrder);
        Mockito.verify(snackOrderRepository, Mockito.times(1)).save(productOrder);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new SnackOrder()))
                .when(snackOrderRepository)
                .findById(1L);
        snackOrderService.findById(1L);
        Mockito.verify(snackOrderRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        snackOrderService.deleteById(1L);
        Mockito.verify(snackOrderRepository, Mockito.times(1)).deleteById(1L);
    }
}