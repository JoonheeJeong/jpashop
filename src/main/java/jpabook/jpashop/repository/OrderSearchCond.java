package jpabook.jpashop.repository;

import jpabook.jpashop.domain.type.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearchCond {

    private final String name;
    private final OrderStatus status;

    public OrderSearchCond() {
        this(null, null);
    }

    public OrderSearchCond(String name) {
        this(name, null);
    }

    public OrderSearchCond(OrderStatus status) {
        this(null, status);
    }

    public OrderSearchCond(String name, OrderStatus status) {
        this.name = name;
        this.status = status;
    }
}
