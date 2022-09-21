package com.example.Coffee.service.order;

import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.order.snack.SnackOrder;
import com.example.Coffee.repository.OrderRepository;
import com.example.Coffee.repository.orderDetails.SnackOrderRepository;
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
public class SnackOrderService {
    private final SnackOrderRepository snackOrderRepository;
    private final OrderRepository orderRepository;

    public Page<SnackOrder> findPageByOrderId(
            Integer currentPage,
            String sortingField,
            String sortingDirection,
            Long orderId) {
        log.info("get snack order page. page: {}, field: {}, direction: {}",
                currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<SnackOrder> snackOrders;
        snackOrders = snackOrderRepository.findAllByOrderId(orderId, pageable);
        log.info("success");
        return snackOrders;
    }

    public SnackOrder save(SnackOrder snackOrder){
        log.info("save snackOrder: {}", snackOrder);
        Order order = orderRepository.findById(1L).orElseThrow();
        snackOrder.setOrder(order);
        snackOrderRepository.save(snackOrder);
        log.info("success");
        return snackOrder;
    }

    public SnackOrder update(SnackOrder snackOrderForm){
        log.info("update snackOrder: {}", snackOrderForm);
        SnackOrder snackOrder = snackOrderRepository.findById(snackOrderForm.getId()).orElseThrow();
        snackOrder.setSnack(snackOrderForm.getSnack());
        snackOrder.setSize(snackOrderForm.getSize());
        snackOrder.setSauce(snackOrderForm.getSauce());
        snackOrder.setSupplement(snackOrderForm.getSupplement());
        snackOrder.setCount(snackOrderForm.getCount());
        snackOrder.setActive(snackOrderForm.isActive());
        Order order = orderRepository.findById(1L).orElseThrow();
        snackOrder.setOrder(order);
        snackOrderRepository.save(snackOrder);
        log.info("success");
        return snackOrder;
    }

    public SnackOrder findById(Long id) {
        log.info("get snackOrder by id: {} ", id);
        SnackOrder snackOrder = snackOrderRepository.findById(id).orElseThrow();
        log.info("success");
        return snackOrder;
    }

    public void deleteById(Long id) {
        log.info("delete snack order by id: {} ", id);
        snackOrderRepository.deleteById(id);
        log.info("success");
    }
}
