package jpabook.jpashop.utils;

import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;

public class OrderItemUtil {

    public static OrderItem newOrderItem(Item item, int quantity) {
        return OrderItem.builder()
                .item(item)
                .totalPrice(item.getPrice() * quantity)
                .quantity(quantity)
                .build();
    }
}
