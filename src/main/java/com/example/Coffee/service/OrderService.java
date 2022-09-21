package com.example.Coffee.service;

import com.example.Coffee.entities.order.order.Order;
import com.example.Coffee.entities.order.order.OrderSpecifications;
import com.example.Coffee.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> findSortingAndSpecificationPage(
            Integer currentPage,
            String sortingField,
            String sortingDirection,
            String id,
            String name,
            String date,
            String time,
            String city,
            String home,
            String entrance,
            String flat
    ) {
        log.info("get order page. page: {}, field: {}, direction: {}, search = id: {} name: {}, date: {}, time: {}",
                currentPage - 1, sortingField, sortingDirection, id, name, date, time);
        Specification<Order> specification = Specification
                .where(OrderSpecifications.likeUserName(name))
                .and(OrderSpecifications.likeId(id))
                .and(OrderSpecifications.likeTime(time))
                .and(OrderSpecifications.likeDate(date))
                .and(OrderSpecifications.likeCityName(city))
                .and(OrderSpecifications.likeHome(home))
                .and(OrderSpecifications.likeEntrance(entrance))
                .and(OrderSpecifications.likeFlat(flat));

        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Order> orders = orderRepository.findAll(specification, pageable);

        log.info("success");
        return orders;
    }

    public Order findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        log.info("get order by id: {}", id);
        return order;
    }

    @Transactional
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
