package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int totalPrice;
    private int quantity;

    public OrderItem(Item item, int quantity) {
        this(item, item.getPrice() * quantity, quantity);
    }

    public OrderItem(Item item, int orderPrice, int quantity) {
        this.item = item;
        this.totalPrice = orderPrice * quantity;
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
