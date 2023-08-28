package jpabook.jpashop.service;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDTO {

    private final Long memberId;
    private final List<Long> itemIds;
    private final List<Integer> quantities;

    public OrderRequestDTO(Long memberId, List<Long> itemIds, List<Integer> quantities) {
        if (itemIds.size() != quantities.size()) {
            throw new IllegalArgumentException("상품의 개수와 상품 수량의 개수가 일치하지 않습니다.");
        }

        this.memberId = memberId;
        this.itemIds = itemIds;
        this.quantities = quantities;
    }
}
