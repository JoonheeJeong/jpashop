package jpabook.jpashop.domain;

import jpabook.jpashop.domain.type.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.READY;

    public Delivery(Address address) {
        this.address = address;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void delivered() {
        status = DeliveryStatus.DELIVERED;
    }
}
