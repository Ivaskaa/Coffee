package com.example.Coffee.service.order;

import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.order.dessert.DessertOrder;
import com.example.Coffee.repository.OrderRepository;
import com.example.Coffee.repository.orderDetails.DessertOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class DessertOrderService {
    private final DessertOrderRepository dessertOrderRepository;
    private final OrderRepository orderRepository;

    public Page<DessertOrder> findPageByOrderId(
            Integer currentPage,
            String sortingField,
            String sortingDirection,
            Long orderId) {
        log.info("get dessert order page. page: {}, field: {}, direction: {}",
                currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<DessertOrder> dessertOrders;
        dessertOrders = dessertOrderRepository.findAllByOrderId(orderId, pageable);
        log.info("success");
        return dessertOrders;
    }

    public DessertOrder save(DessertOrder dessertOrder){
        log.info("save dessertOrder: {}", dessertOrder);
        Order order = orderRepository.findById(1L).orElseThrow();
        dessertOrder.setOrder(order);
        dessertOrderRepository.save(dessertOrder);
        log.info("success");
        return dessertOrder;
    }

    public DessertOrder update(DessertOrder dessertOrderForm){
        log.info("update dessertOrder: {}", dessertOrderForm);
        DessertOrder dessertOrder = dessertOrderRepository.findById(dessertOrderForm.getId()).orElseThrow();
        dessertOrder.setDessert(dessertOrderForm.getDessert());
        dessertOrder.setSize(dessertOrderForm.getSize());
        dessertOrder.setSupplement(dessertOrderForm.getSupplement());
        dessertOrder.setSyrup(dessertOrderForm.getSyrup());
        dessertOrder.setCount(dessertOrderForm.getCount());
        dessertOrder.setActive(dessertOrderForm.isActive());
        Order order = orderRepository.findById(1L).orElseThrow();
        dessertOrder.setOrder(order);
        dessertOrderRepository.save(dessertOrder);
        log.info("success");
        return dessertOrder;
    }

    public DessertOrder findById(Long id) {
        log.info("get dessertOrder by id: {} ", id);
        DessertOrder dessertOrder = dessertOrderRepository.findById(id).orElseThrow();
        log.info("success");
        return dessertOrder;
    }

    public void deleteById(Long id) {
        log.info("delete dessert order by id: {} ", id);
        dessertOrderRepository.deleteById(id);
        log.info("success");
    }

}
