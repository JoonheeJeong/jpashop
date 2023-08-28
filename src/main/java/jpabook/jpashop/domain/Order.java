package jpabook.jpashop.domain;

import jpabook.jpashop.domain.type.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @CreationTimestamp
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDER;

    public Order(Member member, @NonNull OrderItem... orderItems) {
        this(member, new Delivery(member.getAddress()), orderItems);
    }

    public Order(Member member, Delivery delivery, @NonNull OrderItem... orderItems) {
        setMember(member);
        setDelivery(delivery);
        Arrays.stream(orderItems).forEach(this::addOrderItems);
    }

    private void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    private void addOrderItems(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    private void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void cancel() {
        status = OrderStatus.CANCEL;
        orderItems.forEach(OrderItem::cancel);
    }
}
