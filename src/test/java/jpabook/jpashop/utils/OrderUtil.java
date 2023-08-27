package jpabook.jpashop.utils;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import java.util.Arrays;

public class OrderUtil {

    public static Order newOrder(Member member, OrderItem... orderItems) {
        Order order = Order.builder()
                .member(member)
                .build();
        Arrays.stream(orderItems).forEach(order::addOrderItems);
        return order;
    }
}
