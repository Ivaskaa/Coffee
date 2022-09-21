package com.example.Coffee.service.order;

import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.order.tea.TeaOrder;
import com.example.Coffee.repository.OrderRepository;
import com.example.Coffee.repository.orderDetails.TeaOrderRepository;
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
public class TeaOrderService {

    private final TeaOrderRepository teaOrderRepository;
    private final OrderRepository orderRepository;

    public Page<TeaOrder> findPageByOrderId(
            Integer currentPage,
            String sortingField,
            String sortingDirection,
            Long orderId) {
        log.info("get tea order page. page: {}, field: {}, direction: {}",
                currentPage - 1, sortingField, sortingDirection);
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<TeaOrder> teaOrders;
        teaOrders = teaOrderRepository.findAllByOrderId(orderId, pageable);
        log.info("success");
        return teaOrders;
    }

    public TeaOrder save(TeaOrder teaOrder){
        log.info("save teaOrder: {}", teaOrder);
        Order order = orderRepository.findById(1L).orElseThrow();
        teaOrder.setOrder(order);
        teaOrderRepository.save(teaOrder);
        log.info("success");
        return teaOrder;
    }

    public TeaOrder update(TeaOrder teaOrderForm){
        log.info("update teaOrder: {}", teaOrderForm);
        TeaOrder teaOrder = teaOrderRepository.findById(teaOrderForm.getId()).orElseThrow();
        teaOrder.setTea(teaOrderForm.getTea());
        teaOrder.setSize(teaOrderForm.getSize());
        teaOrder.setMilk(teaOrderForm.getMilk());
        teaOrder.setSyrup(teaOrderForm.getSyrup());
        teaOrder.setSupplement(teaOrderForm.getSupplement());
        teaOrder.setCount(teaOrderForm.getCount());
        teaOrder.setActive(teaOrderForm.isActive());
        Order order = orderRepository.findById(1L).orElseThrow();
        teaOrder.setOrder(order);
        teaOrderRepository.save(teaOrder);
        log.info("success");
        return teaOrder;
    }

    public TeaOrder findById(Long id) {
        log.info("get teaOrder by id: {} ", id);
        TeaOrder teaOrder = teaOrderRepository.findById(id).orElseThrow();
        log.info("success");
        return teaOrder;
    }

    public void deleteById(Long id) {
        log.info("delete teaOrder by id: {} ", id);
        teaOrderRepository.deleteById(id);
        log.info("success");
    }
}
