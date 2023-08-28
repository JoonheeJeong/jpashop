package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    void save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAllByLikeNameAndStatus(OrderSearchCond cond);
}
