package com.example.Coffee.service.order;

import com.example.Coffee.entities.order.coffee.CoffeeOrder;
import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.repository.OrderRepository;
import com.example.Coffee.repository.orderDetails.CoffeeOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@AllArgsConstructor
public class CoffeeOrderService {
    private final CoffeeOrderRepository coffeeOrderRepository;
    private final OrderRepository orderRepository;

    public Page<CoffeeOrder> findPageByOrderId(
            Integer currentPage,
            String sortingField,
            String sortingDirection,
            Long orderId) {
        log.info("get coffee order page. page: {}, field: {}, direction: {}",
                currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<CoffeeOrder> coffeeOrders;
        coffeeOrders = coffeeOrderRepository.findAllByOrderId(orderId, pageable);
        log.info("success");
        return coffeeOrders;
    }

    public CoffeeOrder save(CoffeeOrder coffeeOrder){
        log.info("save coffeeOrder: {}", coffeeOrder);
        Order order = orderRepository.findById(1L).orElseThrow();
        coffeeOrder.setOrder(order);
        coffeeOrderRepository.save(coffeeOrder);
        log.info("success");
        return coffeeOrder;
    }

    public CoffeeOrder update(CoffeeOrder coffeeOrderForm){
        log.info("update coffeeOrder: {}", coffeeOrderForm);
        CoffeeOrder coffeeOrder = coffeeOrderRepository.findById(coffeeOrderForm.getId()).orElseThrow();
        coffeeOrder.setCoffee(coffeeOrderForm.getCoffee());
        coffeeOrder.setSize(coffeeOrderForm.getSize());
        coffeeOrder.setAlcohol(coffeeOrderForm.getAlcohol());
        coffeeOrder.setSyrup(coffeeOrderForm.getSyrup());
        coffeeOrder.setSupplement(coffeeOrderForm.getSupplement());
        coffeeOrder.setMilk(coffeeOrderForm.getMilk());
        coffeeOrder.setCount(coffeeOrderForm.getCount());
        coffeeOrder.setActive(coffeeOrderForm.isActive());
        Order order = orderRepository.findById(1L).orElseThrow();
        coffeeOrder.setOrder(order);
        coffeeOrderRepository.save(coffeeOrder);
        log.info("success");
        return coffeeOrder;
    }

    public CoffeeOrder findById(Long id) {
        log.info("get coffee order by id: {} ", id);
        CoffeeOrder coffeeOrder = coffeeOrderRepository.findById(id).orElseThrow();
        log.info("success");
        return coffeeOrder;
    }

    public void deleteById(Long id) {
        log.info("delete coffee order by id: {} ", id);
        coffeeOrderRepository.deleteById(id);
        log.info("success");
    }
}
