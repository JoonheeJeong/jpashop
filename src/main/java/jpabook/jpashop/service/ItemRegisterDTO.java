package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@SuperBuilder
public abstract class ItemRegisterDTO {

    private Long id;
    private String name;
    private int price;
    private int stackQuantity;

    public abstract Item toItem();
}
