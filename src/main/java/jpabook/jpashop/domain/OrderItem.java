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

    private int orderPrice;
    private int quantity;

    @Transient
    private int totalPrice;

    public OrderItem(Item item, int quantity) {
        this(item, item.getPrice(), quantity);
    }

    public OrderItem(Item item, int orderPrice, int quantity) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
        this.totalPrice = orderPrice * quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void cancel() {
        item.restoreStock(quantity);
    }
}
