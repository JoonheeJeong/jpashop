package jpabook.jpashop.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class ItemUpdateDTO {

    private Long id;
    private String name;
    private int price;
    private int stackQuantity;
}
