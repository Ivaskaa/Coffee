package com.example.Coffee.service.order;

import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.order.sandwich.SandwichOrder;
import com.example.Coffee.repository.OrderRepository;
import com.example.Coffee.repository.orderDetails.SandwichOrderRepository;
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
public class SandwichOrderService {
    private final SandwichOrderRepository sandwichOrderRepository;
    private final OrderRepository orderRepository;

    public Page<SandwichOrder> findPageByOrderId(
            Integer currentPage,
            String sortingField,
            String sortingDirection,
            Long orderId) {
        log.info("get sandwich order page. page: {}, field: {}, direction: {}",
                currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<SandwichOrder> sandwichOrders;
        sandwichOrders = sandwichOrderRepository.findAllByOrderId(orderId, pageable);
        log.info("success");
        return sandwichOrders;
    }

    public SandwichOrder save(SandwichOrder sandwichOrder){
        log.info("save sandwichOrder: {}", sandwichOrder);
        Order order = orderRepository.findById(1L).orElseThrow();
        sandwichOrder.setOrder(order);
        sandwichOrderRepository.save(sandwichOrder);
        log.info("success");
        return sandwichOrder;
    }

    public SandwichOrder update(SandwichOrder sandwichOrderForm){
        log.info("update sandwichOrder: {}", sandwichOrderForm);
        SandwichOrder sandwichOrder = sandwichOrderRepository.findById(sandwichOrderForm.getId()).orElseThrow();
        sandwichOrder.setSandwich(sandwichOrderForm.getSandwich());
        sandwichOrder.setSize(sandwichOrderForm.getSize());
        sandwichOrder.setSauce(sandwichOrderForm.getSauce());
        sandwichOrder.setSupplement(sandwichOrderForm.getSupplement());
        sandwichOrder.setCount(sandwichOrderForm.getCount());
        sandwichOrder.setActive(sandwichOrderForm.isActive());
        Order order = orderRepository.findById(1L).orElseThrow();
        sandwichOrder.setOrder(order);
        sandwichOrderRepository.save(sandwichOrder);
        log.info("success");
        return sandwichOrder;
    }

    public SandwichOrder findById(Long id) {
        log.info("get sandwichOrder by id: {} ", id);
        SandwichOrder sandwichOrder = sandwichOrderRepository.findById(id).orElseThrow();
        log.info("success");
        return sandwichOrder;
    }

    public void deleteById(Long id) {
        log.info("delete sandwich order by id: {} ", id);
        sandwichOrderRepository.deleteById(id);
        log.info("success");
    }
}
